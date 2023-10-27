import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class User {
    private String name;
    private String surName;
    private String patronymic;
    private LocalDate birthDate;
    private int phoneNumber;
    private char gender;
    static final int DataLength = 6;

    User(String name, String surName, String patronymic,
         LocalDate birthDate, int phoneNumber, char gender) {
        this.name = name;
        this.surName = surName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;

    }

    public User() {
        createUser(dataUserInput());
    }

    public String dataUserInput(){

        Scanner scanner = new Scanner(System.in);

        System.out.printf(  "Введите следующие данные через пробел" +
                            "(Имя, Фамилия, Отчество, Дата рождения (в формате dd.mm.yyyy)" +
                            ", номер телефона, пол (f или m)):\n");

        String data = scanner.nextLine();
        return data;
    }

    public static User createUser(String input) {
        try {
            validateDataAmount(input);
            validateDataTypes(input);
        } catch (CustomDataAmountException e) {
            System.out.println("Количество данных: " + e.getDataLength());
        } catch (CustomDataTypeException e) {
            System.out.println("Неверный формат: " + e.getDataName());
        }

        createFile(input);

        String[] parsedData = input.split(" ");
        String name = parsedData[0];
        String surName = parsedData[1];
        String patronymic = parsedData[2];
        LocalDate birth = LocalDate.parse(parsedData[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int phone = Integer.parseInt(parsedData[4]);
        char gender = parsedData[5].charAt(0);
        return new User(name, surName, patronymic, birth, phone, gender);
    }

    static void validateDataAmount(String data) {
        String[] parsedData = data.split(" ");
        int dataLength = parsedData.length;

        if (dataLength < DataLength) {
            throw new CustomDataAmountException("Вы ввели меньше данных, чем требуется!", dataLength);
        }
        if (dataLength > DataLength) {
            throw new CustomDataAmountException("Вы ввели больше данных, чем требуется!", dataLength);
        }
    }

    static void validateDataTypes(String data) {
        String[] parsedData = data.split(" ");
        String name = parsedData[0];
        if (!name.chars().allMatch(Character::isLetter)) {
            throw new CustomDataTypeException("Неверный формат данных!", "Имя");
        }
        String surName = parsedData[1];
        if (!surName.chars().allMatch(Character::isLetter)) {
            throw new CustomDataTypeException("Неверный формат данных!", "Фамилия");
        }
        String patronymic = parsedData[2];
        if (!patronymic.chars().allMatch(Character::isLetter)) {
            throw new CustomDataTypeException("Неверный формат данных!", "Отчество");
        }
        try {
            LocalDate birth = LocalDate.parse(parsedData[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            throw new CustomDataTypeException("Неверный формат данных!", "Дата рождения");
        }
        String phoneNumber = parsedData[4];
        try {
            int number = Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            throw new CustomDataTypeException("Неверный формат данных!", "Номер телефона");
        }
        String gender = parsedData[5];
        if (!(gender.length() == 1) || (!gender.equals("f") && !gender.equals("m"))) {
            throw new CustomDataTypeException("Неверный формат данных!", "Пол");
        }
    }

	static void createFile(String input){

        String[] parsedData = input.split(" ");
        String userInfo =   parsedData[0] + " " +
                            parsedData[1] + " " +
                            parsedData[2] + " " +
                            (LocalDate.parse(parsedData[3], DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
                            Integer.parseInt(parsedData[4]) + " " +
                            parsedData[5].charAt(0));
        String fileName = parsedData[0]  + ".txt";
        File file = new File(fileName);
        if (file.exists()){
            System.out.printf("Пользователь с фамилией %s существует!", parsedData[0]);
            System.out.printf("Дописываем информацию о новом пользователе-однофамильце в файл %s!", fileName);
            try (FileWriter fileWriter = new FileWriter(fileName, true)){
                fileWriter.write("\n");
                fileWriter.write(userInfo);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Такого файла не существует!");
            System.out.println("Будет создан новый файл!");
            try (FileWriter fileWriter = new FileWriter(fileName)){
                fileWriter.write(userInfo);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
	}
}