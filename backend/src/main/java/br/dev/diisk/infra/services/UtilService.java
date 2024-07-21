package br.dev.diisk.infra.services;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

    public String nameToKey(String name) {
        return removeAccents(name).toLowerCase().trim()
                .replaceAll("\\s+", " ")
                .replaceAll(" ", "-")
                .replaceAll("-[a-z]{1,2}-", "-");
    }

    public String onlyNumbers(String input) {
        String result = input.replaceAll("[\\D,\\.]", "");
        return result;
    }

    public String removeAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");
        return result;
    }

}
