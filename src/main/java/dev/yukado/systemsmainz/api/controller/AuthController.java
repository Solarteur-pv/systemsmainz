package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.aop.Audit;
import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.entity.HomeCard;
import dev.yukado.systemsmainz.entity.Product;
import dev.yukado.systemsmainz.service.banner.BannerService;
import dev.yukado.systemsmainz.service.homecard.HomeCardService;
import dev.yukado.systemsmainz.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class AuthController {

        private final BannerService bannerService;
        private final HomeCardService homeCardService;
        private final ProductService productService;

    public AuthController(BannerService bannerService, HomeCardService homeCardService, ProductService productService) {
        this.bannerService = bannerService;
        this.homeCardService = homeCardService;
        this.productService = productService;
    }


    @GetMapping("/")
    public String getHome(Model model) {
        List<Banner> banners = bannerService.findAll();
        List<HomeCard> cards = homeCardService.findAll();
        model.addAttribute("cards", cards);
        model.addAttribute("banners", banners);
        return "index";
    }
    // Standard User
    @Audit(action = "VIEW_LOGIN_PAGE")
    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("headerFragment", "fragments/header :: header");

        return "login";
    }

    @GetMapping("/about")
    public String getAbout() {

        return "about";
    }
    @GetMapping("/referenzen")
    public String getReferenzen() {

        return "referenzen";
    }

    @GetMapping("/homecard/image/{id}")
    public ResponseEntity<byte[]> getHomeCardImage(@PathVariable Long id) {
        HomeCard card = homeCardService.findById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(card.getContentType()))
                .body(card.getData());
    }

    @GetMapping("/banner/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Banner banner = bannerService.findById(id);

        if (banner == null || banner.getData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(banner.getContentType()))
                .body(banner.getData());
    }

    @GetMapping("/shop")
    public String showShop(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productsPage = productService.findPaginatedProducts(pageable);

        model.addAttribute("productsPage", productsPage);
        model.addAttribute("products", productsPage.getContent());

        return "shop";
    }
}
