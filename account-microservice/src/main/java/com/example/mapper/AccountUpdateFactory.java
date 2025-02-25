package com.example.mapper;

import com.example.entity.Account;
import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class AccountUpdateFactory {

    public static void updateFields(Account updatedAccount, Account account) {

        String city = account.getCity();
        updatedAccount.setCity(city == null || city.isBlank() ? null : account.getCity());

        String country = account.getCountry();
        updatedAccount.setCountry(country == null || country.isBlank() ? null : country);

        if (account.getFirstName() != null && !account.getFirstName().isEmpty()) {
            updatedAccount.setFirstName(account.getFirstName().trim().toUpperCase());
        }
        if (account.getLastName() != null && !account.getLastName().isEmpty()) {
            updatedAccount.setLastName(account.getLastName().trim().toUpperCase());
        }

        updatedAccount.setBirthDate(account.getBirthDate());

        String phone = formatPhone(account.getPhone());
        if (phone != null) {
            updatedAccount.setPhone(phone);
        }

        String about = account.getAbout();
        updatedAccount.setAbout(about == null || about.isBlank() ? null : about);

    }

    private String formatPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return null;
        }

        String digitsOnly = phone.replaceAll("[^\\d]", "");

        Pattern pattern = Pattern.compile("^(7|8)(\\d{10})$");
        Matcher matcher = pattern.matcher(digitsOnly);

        if (matcher.matches()) {
            return "7" + matcher.group(2);
        } else if (digitsOnly.length() == 10) {
            return "7" + digitsOnly;
        }

        return null;
    }

}