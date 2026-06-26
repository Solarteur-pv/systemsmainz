package dev.yukado.systemsmainz.api.controller;


import dev.yukado.systemsmainz.aop.Audit;
import dev.yukado.systemsmainz.dto.BannerDto;
import dev.yukado.systemsmainz.dto.ProductCreateDto;
import dev.yukado.systemsmainz.dto.UserDto;
import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.entity.User;
import dev.yukado.systemsmainz.service.banner.BannerService;
import dev.yukado.systemsmainz.service.product.ProductService;
import dev.yukado.systemsmainz.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final BannerService bannerService;
    private final ProductService productService;

    public AdminController(UserDetailsService userDetailsService, UserService userService, BannerService bannerService, ProductService productService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.bannerService = bannerService;
        this.productService = productService;
    }

    @Audit(action = "VIEW_ADMIN_DASHBOARD")
    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @Audit(action = "VIEW_ADMIN_BANNERS")
    @GetMapping("/admin/banner")
    public String getBanner(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();

        String userName =  "A" + id1;
        List<Banner> banners = bannerService.findAll();

        model.addAttribute("banners", banners);
        model.addAttribute("banner", new BannerDto());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        return "admin/banner";  // corresponding to about.html
    }

    @Audit(action = "ADMIN_BANNER_UPLOADED")
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

    @Audit(action = "VIEW_ADMIN_BANNER_EDIT")
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

    @Audit(action = "ADMIN_BANNER_EDITED")
    @PostMapping("/admin/banner_edit")
    public String updateBanner(@ModelAttribute BannerDto dto,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {

        bannerService.update(dto, file);

        redirectAttributes.addFlashAttribute("message", "Banner gespeichert!");
        return "redirect:/admin/banner";
    }

    @Audit(action = "VIEW_ADMIN_HOMECARDS")
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

    @Audit(action = "ADMIN_HOMECARD_UPLOADED")
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

    @Audit(action = "VIEW_ADMIN_HOMECARD_EDIT")
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

        return "admin/edithomecard";
    }

    @Audit(action = "ADMIN_HOMECARD_EDITED")
    @PostMapping("/admin/homecard_edit")
    public String updateHomeCard(@ModelAttribute BannerDto dto,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {

        bannerService.update(dto, file);

        redirectAttributes.addFlashAttribute("message", "Banner gespeichert!");
        return "redirect:/admin/homecard";
    }

    @Audit(action = "VIEW_ADMIN_USERS")
    @GetMapping("/admin/users")
    public String getUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String success,
            @RequestParam(required = false) String updated,
            Model model,
            Principal principal,
            Pageable pageable) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);

        Page<User> usersPage = userService.findPaginatedUsers(search, pageable);

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);
        model.addAttribute("usersPage", usersPage);
        model.addAttribute("search", search);

        // wichtig für Formulare
        model.addAttribute("userDto", new UserDto());

        // Meldungen
        if (success != null) {
            model.addAttribute("message", "Registrierung erfolgreich!");
        }

        if (updated != null) {
            model.addAttribute("message", "Benutzer wurde erfolgreich aktualisiert!");
        }

        return "admin/users";
    }

    @Audit(action = "ADMIN_USER_CREATED")
    @PostMapping("/admin/reg_users")
    public String createUser(
            @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/users";
        }

        userService.save(userDto);

        return "redirect:/admin/users?success";
    }

    @Audit(action = "ADMIN_USER_UPDATED")
    @PostMapping("/admin/update_user")
    public String updateUser(
            @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/users"; // gleiche Seite erneut anzeigen
        }

        userService.updateUser(userDto);

        return "redirect:/admin/users?updated";
    }

    @Audit(action = "VIEW_ADMIN_PRODUCT_CREATED")
    @GetMapping("/admin/product_create")
    public String showCreateProductForm(
            @RequestParam(required = false) String success,
            Model model) {

        model.addAttribute("productDto", new ProductCreateDto());
        model.addAttribute("categories", productService.getAllCategories());

        if (success != null) {
            model.addAttribute("message", "Produkt erfolgreich angelegt!");
        }

        return "admin/product_create";
    }

    @Audit(action = "ADMIN_PRODUCT_CREATED")
    @PostMapping("/admin/product_create")
    public String createProduct(
            @ModelAttribute("productDto") ProductCreateDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/product_create";
        }

        productService.createProduct(dto);

        return "redirect:/admin/product_create?success";
    }



}

