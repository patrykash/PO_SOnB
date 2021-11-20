package com.example.sonb.service;

public class BergerService {

    private static final Long MAX_NUMBER_ON_BERGER_LENGTH = 31L;

    static String createBergerCode(String input) {
        Long numberOfOnes = countNumberOfOnes(input);
        System.out.println("Liczba jedynek " + numberOfOnes);
        Long negatedNumberOfOnes = numberOfOnes ^ MAX_NUMBER_ON_BERGER_LENGTH;
        String bergerCode = Long.toBinaryString(negatedNumberOfOnes);
        return  bergerCode;
    }

     static Long countNumberOfOnes(String input){
        return input.chars().filter(ch -> ch=='1').count();
    }

    static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        for (char aChar : input.toCharArray()) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );

        }
        return result.toString();
    }

    static Long decodeBerger(String input) {
        return Long.parseUnsignedLong(input, 2) ^ MAX_NUMBER_ON_BERGER_LENGTH;
    }

    static String getBergerCodeFromMessage(String message) {
        return message.substring(16);
    }



}
