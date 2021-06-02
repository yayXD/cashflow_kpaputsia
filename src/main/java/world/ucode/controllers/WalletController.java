package world.ucode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import world.ucode.domain.*;
import world.ucode.repos.CategoryRepo;
import world.ucode.repos.CurrencyRepo;
import world.ucode.repos.TagRepo;
import world.ucode.repos.WalletRepo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
public class WalletController {
    private static ArrayList<Integer> itemNumbers = new ArrayList<>();

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private TagRepo tagRepo;

    @GetMapping("/wallet")
    public String main(Map<String, Object> model) {
        return "wallet";
    }

    @PostMapping("/wallet")
     public String add(@RequestParam String name, @RequestParam String currency,
                       //@RequestParam String ownerLogin,
                       @AuthenticationPrincipal Registration registration,
                      @RequestParam Double balance, @RequestParam("categoryEx") String categoryEx,
                       @RequestParam String tag, Map<String, Object> model) {

        makeCategory();
        Currency c = currencyRepo.findByCurName(currency);
        if(c == null && currency.equals("gryvna") == true) {
            Currency cu = new Currency("gryvna", "UAH");
            currencyRepo.save(cu);
        }
            if(c == null && currency.equals("dollar") == true) {
            Currency currency2 = new Currency("dollar","USD");
            currencyRepo.save(currency2);
        }
         Wallet reg = walletRepo.findByName(name);
        c = currencyRepo.findByCurName(currency);
        if (reg == null && c != null) {
            if(balance != null) {
                int itemNumber = makeItemNumber();
                Category cat = categoryRepo.findByCategoryName(categoryEx);
                Tag t = tagRepo.findByTagName(tag);
                if (t == null) {
                    Tag ta = new Tag(tag, tag);
                    tagRepo.save(ta);
                }
                t = tagRepo.findByTagName(tag);
                Date data = new Date();
                Wallet wallet = new Wallet(name, balance, c, registration, data.toString(), itemNumber, cat, t);
                walletRepo.save(wallet);
                model.put("message", "Вы создали новый кошелек");
            } else
                model.put("message", "Не возможно создать такой кошелек. Заполните поле баланс");
        } else
            model.put("message", "Не возможно создать такой кошелек. Это имя кошелька уже занято");
        return "wallet";
    }

    public void makeCategory() {
        String[] cat = new String[]{"purchase", "communal", "services", "connection", "taxi", "tickets", "transfer"};
        for( int i = 0; i < 7; i++) {
            Category c = categoryRepo.findByCategoryName(cat[i]);
            if(c == null) {
                Category ca = new Category(cat[i], cat[i]);
                categoryRepo.save(ca);
            }
        }
    }

    public int makeItemNumber() {
        int y = 0;
        SecureRandom rand = new SecureRandom();
        y = rand.nextInt(99999999 - 10000000);
        while (itemNumbers.contains(y) == true) {
            rand = new SecureRandom();
            y = rand.nextInt(99999999 - 10000000);
        }
        itemNumbers.add(y);
        return y;
    }
}