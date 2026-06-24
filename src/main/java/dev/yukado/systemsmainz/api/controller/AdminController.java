package dev.yukado.systemsmainz.api.controller;


import dev.yukado.systemsmainz.dto.BannerDto;
import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.entity.User;
import dev.yukado.systemsmainz.service.banner.BannerService;
import dev.yukado.systemsmainz.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Controller("/admin")
public class AdminController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    BannerService bannerService;

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/banner")
    public String getBanner(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);
        String role = user.getRole();

        String userName =  "A" + id1;
        List<Banner> banners = bannerService.findAll();

        model.addAttribute("banners", banners);
        model.addAttribute("banner", new BannerDto());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        return "admin/banner";  // corresponding to about.html
    }

    @PostMapping("/admin/bannerupload")
    public String uploadBanner(@RequestParam("file") MultipartFile file,
                               @RequestParam("banner_text") String bannerText,
                               @RequestParam("active") Boolean active,
                               Principal principal,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);


        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        BannerDto banner = new BannerDto();
        banner.setFilename(file.getOriginalFilename());
        banner.setBannerText(bannerText);
        banner.setActive(active);       // <-- WICHTIG!
        banner.setCreated_at(createdAt);
        banner.setContentType(file.getContentType());

        bannerService.save(banner, file);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", "A" + user.getId());
        redirectAttributes.addFlashAttribute("message", "Upload erfolgreich!");
        return "redirect:/admin/banner";
    }

    @GetMapping("/admin/banner_edit")
    public String editBanner(@RequestParam("id") Long id,
                             Model model,
                             Principal principal) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);
        String role = user.getRole();
        String userName = "A" + id1;

        Banner banner = bannerService.findById(id);

        // DTO befüllen
        BannerDto dto = new BannerDto();
        dto.setId(banner.getId());
        dto.setFilename(banner.getFilename());
        dto.setBannerText(banner.getBannerText());
        dto.setActive(banner.getActive());
        dto.setCreated_at(banner.getCreated_at());
        dto.setContentType(banner.getContentType());
        // data NICHT ins DTO packen

        model.addAttribute("banner", dto);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        return "admin/editbanner";
    }

    @PostMapping("/admin/banner_edit")
    public String updateBanner(@ModelAttribute BannerDto dto,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {

        bannerService.update(dto, file);

        redirectAttributes.addFlashAttribute("message", "Banner gespeichert!");
        return "redirect:/admin/banner";
    }

    @GetMapping("/admin/homecard")
    public String getHomeCard(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);
        String role = user.getRole();

        String userName =  "A" + id1;
        List<Banner> banners = bannerService.findAll();

        model.addAttribute("banners", banners);
        model.addAttribute("banner", new BannerDto());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        return "admin/homecard";  // corresponding to about.html
    }

    @PostMapping("/admin/homecardupload")
    public String uploadHomeCard(@RequestParam("file") MultipartFile file,
                               @RequestParam("banner_text") String bannerText,
                               @RequestParam("active") Boolean active,
                               Principal principal,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);


        Timestamp createdAt = new Timestamp(System.currentTimeMillis());

        BannerDto banner = new BannerDto();
        banner.setFilename(file.getOriginalFilename());
        banner.setBannerText(bannerText);
        banner.setActive(active);       // <-- WICHTIG!
        banner.setCreated_at(createdAt);
        banner.setContentType(file.getContentType());

        bannerService.save(banner, file);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", "A" + user.getId());
        redirectAttributes.addFlashAttribute("message", "Upload erfolgreich!");
        return "redirect:/admin/homecard";
    }

    @GetMapping("/admin/homecard_edit")
    public String editHomeCard(@RequestParam("id") Long id,
                             Model model,
                             Principal principal) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);
        String role = user.getRole();
        String userName = "A" + id1;

        Banner banner = bannerService.findById(id);

        // DTO befüllen
        BannerDto dto = new BannerDto();
        dto.setId(banner.getId());
        dto.setFilename(banner.getFilename());
        dto.setBannerText(banner.getBannerText());
        dto.setActive(banner.getActive());
        dto.setCreated_at(banner.getCreated_at());
        dto.setContentType(banner.getContentType());
        // data NICHT ins DTO packen

        model.addAttribute("banner", dto);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        return "admin/editbanner";
    }

    @PostMapping("/admin/homecard_edit")
    public String updateHomeCard(@ModelAttribute BannerDto dto,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {

        bannerService.update(dto, file);

        redirectAttributes.addFlashAttribute("message", "Banner gespeichert!");
        return "redirect:/admin/homecard";
    }
}

