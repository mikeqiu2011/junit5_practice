package com.realestateapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {
    private RandomApartmentGenerator randomApartmentGenerator;

    @BeforeEach
    void setUp() {
        this.randomApartmentGenerator = new RandomApartmentGenerator();
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class RateApartmentTests {
        @ParameterizedTest
        @CsvFileSource(resources = "/cheap_departments.csv",numLinesToSkip = 1)
        void should_ReturnZero_when_RateAsCheap(double area, BigDecimal price){
            //given
            Apartment apartment = new Apartment(area,price);

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(0, actual);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/moderate_departments.csv",numLinesToSkip = 1)
        void should_ReturnOne_when_RateAsModerate(double area, BigDecimal price){
            //given
            Apartment apartment = new Apartment(area,price);

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(1, actual);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/expensive_departments.csv",numLinesToSkip = 1)
        void should_ReturnTwo_when_RateAsExpensive(double area, BigDecimal price){
            //given
            Apartment apartment = new Apartment(area,price);

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(2, actual);
        }

        @Test
        void should_ReturnMinusOne_when_AreaIsZero(){
            //given
            Apartment apartment = new Apartment(0,new BigDecimal(100));

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(-1, actual);
        }

    }

    @Nested
    class CalculateAverageRatingTests {

        @Test
        void should_ThrowRunTimeException_when_ListIsEmpty(){
            //given
            List<Apartment> apartments = new ArrayList<Apartment>();

            //when
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);

            //then
            assertThrows(RuntimeException.class, executable);
        }

        @Test
        void should_ReturnReasonableAveragePrice_when_ListIsNotEmpty(){
            //given
            List<Apartment> apartments = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                apartments.add(ApartmentRaterTest.this.randomApartmentGenerator.generate());
            }

            //when
            double actual = ApartmentRater.calculateAverageRating(apartments);
//            System.out.println("actual: " + actual);

            //then
            assertAll(
                    () -> assertTrue(actual > 0),
                    () -> assertTrue(actual < 2)
            );

        }

        @Test
        void should_ReturnRunWithIn500Ms_when_1000ListItemsIsGiven(){
            //given
            List<Apartment> apartments = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                apartments.add(ApartmentRaterTest.this.randomApartmentGenerator.generate());
            }

            //when
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);

            assertTimeout(Duration.ofMillis(500), executable);

        }

    }
}