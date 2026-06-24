package dev.yukado.systemsmainz.api.controller;

import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.entity.HomeCard;
import dev.yukado.systemsmainz.service.banner.BannerService;
import dev.yukado.systemsmainz.service.homecard.HomeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class AuthController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private HomeCardService homeCardService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<Banner> banners = bannerService.findAll();
        List<HomeCard> cards = homeCardService.findAll();
        model.addAttribute("cards", cards);
        model.addAttribute("banners", banners);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
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

    @GetMapping("/products")
    public String getProducts() {

        return "products";
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
}
