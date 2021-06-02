package world.ucode.controllers;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.supercsv.io.CsvBeanWriter;
//import org.supercsv.io.ICsvBeanWriter;
//import org.supercsv.prefs.CsvPreference;
import world.ucode.domain.Registration;
import world.ucode.domain.Transaction;
import world.ucode.domain.Wallet;
import world.ucode.repos.CurrencyRepo;
import world.ucode.repos.TransactionRepo;
import world.ucode.repos.WalletRepo;

import javax.persistence.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal Registration registration, Model model) {
        Iterable<Wallet> wal = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wallets", wal);
        return "main";
    }

    @GetMapping("/main/{itemNumber}")
    public String add(@AuthenticationPrincipal Registration registration, @PathVariable int itemNumber, ModelMap model) {
        Iterable<Wallet> wal = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wallets", wal);
        Wallet w = walletRepo.findByItemNumber(itemNumber);
        List<Transaction> tr = transactionRepo.findByWalletName(w);
        model.addAttribute("trans", tr);
        model.addAttribute("walletId", itemNumber);
        return "/main";
    }

    @GetMapping ("/main/export/{walletId}")
    public String exportToCSV(@AuthenticationPrincipal Registration registration, HttpServletResponse response, @PathVariable int walletId, ModelMap model) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=wallet_" + walletId + "_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
        Iterable<Wallet> wal = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wallets", wal);
        Wallet w = walletRepo.findByItemNumber(walletId);
        List<Transaction> tr = transactionRepo.findByWalletName(w);
        model.addAttribute("trans", tr);
        return "/main";
    }

    @GetMapping("/main/deleteAllTransactions/{walletId}")
    public String deleteAllTransactions(@AuthenticationPrincipal Registration registration, @PathVariable int walletId, ModelMap model) {
        Iterable<Wallet> wal = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wallets", wal);
        Wallet w = walletRepo.findByItemNumber(walletId);
        List<Transaction> tr = transactionRepo.findByWalletName(w);

        if(tr.size() > 1) {
            Transaction tr2 = tr.get(tr.size() - 1);
            transactionRepo.delete(tr2);
        }
        tr = transactionRepo.findByWalletName(w);
        model.addAttribute("trans", tr);
        model.addAttribute("walletId", walletId);
        return "/main";
    }

    @GetMapping("/main/showAllTransactions")
    public String showAllTransactions(@AuthenticationPrincipal Registration registration, Model model) {
        Iterable<Wallet> wal = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wallets", wal);
       // Wallet w = walletRepo.findByItemNumber(walletId);
        List<Transaction> tr = new ArrayList<Transaction>();
        for(Wallet w2: wal) {
            List<Transaction> t = transactionRepo.findByWalletName(w2);
            if(t != null)
                tr.addAll(t);
        }
        model.addAttribute("trans", tr);
        return "/main";
    }
}
