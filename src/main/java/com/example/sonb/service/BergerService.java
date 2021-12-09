package com.example.sonb.service;

public class BergerService {

    private static final Long MAX_NUMBER_ON_BERGER_LENGTH = 31L;
    private static boolean isErrorCodeActive = false;

    public static boolean isIsErrorCodeActive() {
        return isErrorCodeActive;
    }

    public static void setIsErrorCodeActive(boolean isErrorCodeActive) {
        BergerService.isErrorCodeActive = isErrorCodeActive;
    }

    static String getBergerCode(String input) {
        return createBergerCode(input, MAX_NUMBER_ON_BERGER_LENGTH);
    }

    static String getBergerCode(String input, Long negationNumber) {
        return createBergerCode(input, negationNumber);
    }

    private static String createBergerCode(String input, Long negationNumber) {
        Long numberOfOnes = countNumberOfOnes(input);
        long negatedNumberOfOnes = numberOfOnes ^ negationNumber;
        return Long.toBinaryString(negatedNumberOfOnes);
    }

     static Long countNumberOfOnes(String input){
        return input.chars().filter(ch -> ch=='1').count();
    }

    static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        for (char aChar : input.toCharArray()) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
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
