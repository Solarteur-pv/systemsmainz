package dev.yukado.systemsmainz.api.controller;


import dev.yukado.systemsmainz.aop.Audit;
import dev.yukado.systemsmainz.audit.AuditLog;
import dev.yukado.systemsmainz.audit.AuditLogRepository;
import dev.yukado.systemsmainz.dto.BannerDto;
import dev.yukado.systemsmainz.dto.HomeCardDto;
import dev.yukado.systemsmainz.dto.ProductCreateDto;
import dev.yukado.systemsmainz.dto.UserDto;
import dev.yukado.systemsmainz.entity.Banner;
import dev.yukado.systemsmainz.entity.HomeCard;
import dev.yukado.systemsmainz.entity.Product;
import dev.yukado.systemsmainz.entity.User;
import dev.yukado.systemsmainz.service.banner.BannerService;
import dev.yukado.systemsmainz.service.homecard.HomeCardService;
import dev.yukado.systemsmainz.service.product.ProductService;
import dev.yukado.systemsmainz.service.stats.AdminStatsService;
import dev.yukado.systemsmainz.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

@Controller("/admin")
public class AdminController {


    private final AuditLogRepository auditLogRepository;
    private final AdminStatsService statsService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final BannerService bannerService;
    private final HomeCardService homecardService;
    private final ProductService productService;


    public AdminController(AuditLogRepository auditLogRepository, AdminStatsService statsService, UserDetailsService userDetailsService, UserService userService, BannerService bannerService, HomeCardService homecardService, ProductService productService) {
        this.auditLogRepository = auditLogRepository;
        this.statsService = statsService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.bannerService = bannerService;
        this.homecardService = homecardService;
        this.productService = productService;
    }

    @Audit(action = "VIEW_ADMIN_DASHBOARD")
    @GetMapping("/admin/dashboard")
    public String dashboard(Model model, Principal principal) {

        // --- User / Header / Wallet wie bei dir ---
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        User user = userService.findById(id1);

        String userName = "A" + id1;

        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);
        model.addAttribute("role", user.getRole());
        model.addAttribute("userDetails", userDetails);

        // ---------------------------------------------------------
        //  STATISTIK-DATEN DIREKT INS MODEL (DTO)
        // ---------------------------------------------------------

        model.addAttribute("stats24h", statsService.getStats(Duration.ofHours(24)));
        model.addAttribute("stats7d", statsService.getStats(Duration.ofDays(7)));
        model.addAttribute("stats30d", statsService.getStats(Duration.ofDays(30)));

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

    @Audit(action = "ADMIN_BANNER_DELETED")
    @PostMapping("/banner_delete")
    public String deleteBanner(@RequestParam("id") Long id,
                               RedirectAttributes redirectAttributes) {

        bannerService.delete(id);

        redirectAttributes.addFlashAttribute("message", "Banner gelöscht!");
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
        List<HomeCard> homeCards = homecardService.findAll();

        model.addAttribute("homeCards", homeCards);
        model.addAttribute("HomeCard", new HomeCardDto());
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

        HomeCardDto homecard = new HomeCardDto();
        homecard.setFilename(file.getOriginalFilename());
        homecard.setHomeCardText(bannerText);
        homecard.setActive(active);       // <-- WICHTIG!
        homecard.setCreated_at(createdAt);
        homecard.setContentType(file.getContentType());

        homecardService.save(homecard, file);
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

        HomeCard homeCard = homecardService.findById(id);

        // DTO befüllen
        HomeCardDto dto = new HomeCardDto();
        dto.setId(homeCard.getId());
        dto.setFilename(homeCard.getFilename());
        dto.setHomeCardText(homeCard.getHomeCardText());
        dto.setActive(homeCard.getActive());
        dto.setCreated_at(homeCard.getCreated_at());
        dto.setContentType(homeCard.getContentType());
        // data NICHT ins DTO packen

        model.addAttribute("homeCard", dto);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("userId", id1);

        return "admin/edithomecard";
    }

    @Audit(action = "ADMIN_HOMECARD_EDITED")
    @PostMapping("/admin/homecard_edit")
    public String updateHomeCard(@ModelAttribute HomeCardDto dto,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {

        homecardService.update(dto, file);

        redirectAttributes.addFlashAttribute("message", "HomeCard gespeichert!");
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
        String userName = "A" + id1;
        Page<User> usersPage = userService.findPaginatedUsers(search, pageable);

        model.addAttribute("userDetails", userDetails);
        model.addAttribute("user", user);

        model.addAttribute("usersPage", usersPage);
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("user", user);
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("success", success);
        model.addAttribute("updated", updated);
        model.addAttribute("userName", userName);
        model.addAttribute("userDetails", userDetails);

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

    @Audit(action = "VIEW_ADMIN_EDIT_USER")
    @GetMapping("/admin/edituser/{id}")
    public String editUser(@PathVariable Long id, Model model, Principal principal) {

        User user = userService.findById(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        String userName = "A" + id1;

        if (user == null) {
            model.addAttribute("message", "Benutzer wurde nicht gefunden.");
            return "redirect:/admin/users";
        }
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);
        model.addAttribute("user", user);
        return "admin/edituser";
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

    @GetMapping("/admin/products")
    public String listProducts(
            @RequestParam(required = false) String search,
            Pageable pageable, Principal principal,
            Model model) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        String userName = "A" + id1;
        Page<Product> products = productService.findPaginatedProducts(search, pageable);

        model.addAttribute("products", products);
        model.addAttribute("search", search);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);

        return "admin/products";
    }

    @Audit(action = "VIEW_ADMIN_PRODUCT_CREATED")
    @GetMapping("/admin/product_create")
    public String showCreateProductForm(
            @RequestParam(required = false) String success,
            Model model,
            Principal principal) {

        // Sicherstellen, dass Principal existiert
        if (principal == null) {
            return "redirect:/login";
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Long id1 = userService.findByEmail(userDetails.getUsername()).getId();
        String userName = "A" + id1;

        model.addAttribute("productDto", new ProductCreateDto());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userName", userName);

        if (success != null) {
            model.addAttribute("message", "Produkt erfolgreich angelegt!");
        }

        return "admin/product_create";
    }


    @Audit(action = "ADMIN_PRODUCT_CREATED")
    @PostMapping("/admin/product_new")
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

    @GetMapping("/admin/logs")
    public String viewLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<AuditLog> logs = auditLogRepository.findAll(pageable);

        model.addAttribute("logs", logs.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", logs.getTotalPages());

        return "admin/viewlogs";
    }

}

