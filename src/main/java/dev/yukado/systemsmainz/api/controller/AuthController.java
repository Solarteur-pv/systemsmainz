package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.aop.Audit;
import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.entity.HomeCard;
import dev.yukado.systemsmainz.entity.Product;
import dev.yukado.systemsmainz.service.banner.BannerService;
import dev.yukado.systemsmainz.service.homecard.HomeCardService;
import dev.yukado.systemsmainz.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Audit(action = "VIEW_LOGIN_PAGE")
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {

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

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(required = false) String search,
            Pageable pageable,
            Model model) {

        Page<Product> products = productService.findPaginatedProducts(search, pageable);

        model.addAttribute("products", products);
        model.addAttribute("search", search);

        return "products";
    }

}
