package world.ucode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import world.ucode.domain.*;
import world.ucode.repos.*;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import javax.xml.crypto.Data;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class TransactionController {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private CurrencyRepo currencyRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/transaction")
    public String main(@AuthenticationPrincipal Registration registration, Model model) {
        Iterable<Wallet> wall = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wall", wall);
        return "transaction";
    }

    @PostMapping("/transaction")
    public String add(@AuthenticationPrincipal Registration registration, @RequestParam("tag") String wallName,
                      @RequestParam Double spending,
                      @RequestParam String description, @RequestParam int receiverNumber, Model model) {
        double limit = 1000;
        Wallet wal = walletRepo.findByName(wallName);
        double number = checkLimit(wal);
        if(spending != null) {
            if (wal.getBalance() >= spending) {
                Wallet w = walletRepo.findByItemNumber(receiverNumber);
                if(w != null && receiverNumber != wal.getItemNumber()) {
                    if(limit >= (number + spending)) {
                        Date data = new Date();
                        Transaction tr = new Transaction(wal, "expense", spending, data.toString(), description, receiverNumber);
                        transactionRepo.save(tr);
                        if (wal.getCurrencyNames().getCurName().equals("dollar") == true) {
                            wal.setBalance(wal.getBalance() - spending / 30);
                            double scale = Math.pow(10, 2);
                            wal.setBalance(Math.ceil(wal.getBalance() * scale) / scale);
                        }
                        else {
                            wal.setBalance(wal.getBalance() - spending);
                            double scale = Math.pow(10, 2);
                            wal.setBalance(Math.ceil(wal.getBalance() * scale) / scale);
                        }
                        walletRepo.save(wal);
                        Transaction tr2 = new Transaction(w, "income", spending, data.toString(), description, wal.getItemNumber());
                        transactionRepo.save(tr2);
                        if (w.getCurrencyNames().getCurName().equals("dollar") == true) {
                            w.setBalance(w.getBalance() + spending / 30);
                            double scale = Math.pow(10, 2);
                            w.setBalance(Math.ceil(w.getBalance() * scale) / scale);
                        }
                        else {
                            w.setBalance(w.getBalance() + spending);
                            double scale = Math.pow(10, 2);
                            w.setBalance(Math.ceil(w.getBalance() * scale) / scale);
                        }
                        walletRepo.save(w);
                        model.addAttribute("message", "Вы создали трансакцию");
                    } else
                        model.addAttribute("message", "Вы превысили суточный лимит. Вы можете потратить еще " + (limit - number) + "гривен");
                } else
                    model.addAttribute("message", "Не правильно указан номер карты получателя");

            } else
                model.addAttribute("message", "Недостаточно средств на счету для данной операции");
        } else
            model.addAttribute("message", "Не заполнили поле сумма перевода");
        Iterable<Wallet> wall = walletRepo.findByOwnerLogin(registration);
        model.addAttribute("wall", wall);
        return "transaction";
    }

    public double checkLimit(Wallet w) {
        double limit = 0;
        List<Transaction> t = transactionRepo.findByWalletName(w);
        Date date1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        for (Transaction tr : t) {
            Date date = new Date();
            try {
                date = formatter.parse(tr.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.getTime() < date1.getTime() && date.getTime() > (date1.getTime() + 86400000))
                limit = limit + tr.getSpending();
        }
        return limit;
    }

}
