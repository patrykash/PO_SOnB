package com.example.sonb.service;

public class BergerService {

    private static final Long MAX_NUMBER_ON_BERGER_LENGTH = 31L;
    private static boolean isErrorCodeActive = false;

    public static boolean isErrorCodeActive() {
        return isErrorCodeActive;
    }

    public static void setIsErrorCodeActive(boolean isErrorCodeActive) {
        BergerService.isErrorCodeActive = isErrorCodeActive;
    }

    static String createBergerCode(String message) {
        return createBergerCode(message, MAX_NUMBER_ON_BERGER_LENGTH);
    }

    private static String createBergerCode(String message, Long negationNumber) {
        Long numberOfOnes = countNumberOfOnes(message);
        long negatedNumberOfOnes = numberOfOnes ^ negationNumber;
        String bergerCode =Long.toBinaryString(negatedNumberOfOnes);
        while (bergerCode.length() < 5) {
            bergerCode= "0".concat(bergerCode);
        }
        return bergerCode;
    }

     static Long countNumberOfOnes(String message){
        return message.chars().filter(ch -> ch=='1').count();
    }

    static String convertStringToBinary(String message) {
        StringBuilder result = new StringBuilder();
        for (char aChar : message.toCharArray()) {
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

    public static String getBergerCodeFromMessage(String message) {
        return message.substring(16);
    }

    public static String getMessageContentFromMessage(String message) {
        return message.substring(0, 16);
    }

    public static boolean isMessageCorrect(String message) {
        String bergerCode = getBergerCodeFromMessage(message);
        String messageContent = getMessageContentFromMessage(message);
        long numberOfOnesInMessageContent = countNumberOfOnes(messageContent);
        long decodedBergerCode = decodeBerger(bergerCode);
        System.out.println("numberOfOnesInMessageContent" + numberOfOnesInMessageContent);
        System.out.println("decodedBergerCode" + decodedBergerCode);
        return numberOfOnesInMessageContent == decodedBergerCode;
    }

    public static String convertToMessageWithError(String messageInBinary) {
        boolean isContainsZero = messageInBinary.contains("0");
        String incorrectMessageInBinary;
        if (isContainsZero) {
            incorrectMessageInBinary = messageInBinary.replaceAll("0", "1");
        } else {
            incorrectMessageInBinary = messageInBinary.replaceAll("1", "0");
        }
        System.out.println("message with error: " + incorrectMessageInBinary);
        return incorrectMessageInBinary;
    }
}
