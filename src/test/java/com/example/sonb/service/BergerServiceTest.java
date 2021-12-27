package com.example.sonb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class BergerServiceTest {

    @ParameterizedTest
    @CsvSource({
            "0100000101000001, 11011",
            "0000000000000000, 11111",
            "1111111111111111, 01111",
            "1111111100000000, 10111",


    })
    void shouldCreateCorrectBergerCodeForMessage(String message, String correctBergerCode) {
        String bergerCode = BergerService.createBergerCode(message);

        assertThat(bergerCode).isEqualTo(correctBergerCode);
    }

    @ParameterizedTest
    @CsvSource({
            "0100000101000001, 00100",
            "0000000000000000, 00000",
            "1111111111111111, 10000",
            "1111111100000000, 01000",


    })
    void shouldCreateIncorrectBergerCodeForMessageWhenNegationNumberIsZero(String message, String inCorrectBergerCode) {
        String bergerCode = BergerService.createBergerCode(message,0L);

        assertThat(bergerCode).isEqualTo(inCorrectBergerCode);
    }

    @ParameterizedTest
    @CsvSource({
            "0100000101000001, 4",
            "0000000000000000, 0",
            "1111111111111111, 16",
            "1111111100000000, 8",


    })
    void shouldReturnCorrectNumberOfOnesForMessage(String message, Long correctNumberOfOnes) {
        Long numberOfOnes = BergerService.countNumberOfOnes(message);

        assertThat(numberOfOnes).isEqualTo(correctNumberOfOnes);
    }

    @ParameterizedTest
    @CsvSource({
            "AB, 0100000101000010",
            "b1, 0110001000110001",
            "01, 0011000000110001",
            "!., 0010000100101110",


    })
    void shouldConvertMessageToBinary(String message, String correctMessageInBinary) {
        String messageInBinary = BergerService.convertStringToBinary(message);

        assertThat(messageInBinary).isEqualTo(correctMessageInBinary);
    }

    @ParameterizedTest
    @CsvSource({
            "11011, 4",
            "11111, 0",
            "01111, 16",
            "10111, 8",


    })
    void shouldDecodeBergerCode(String bergerCode, Long correctlyDecodedBerger) {
        Long decodedBerger = BergerService.decodeBerger(bergerCode);

        assertThat(decodedBerger).isEqualTo(correctlyDecodedBerger);
    }
}