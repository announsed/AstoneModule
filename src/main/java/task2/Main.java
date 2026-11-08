package task2;

import task2.dao.UserDao;
import task2.dao.UserDaoImpl;
import task2.model.User;
import task2.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1 - Добавить пользователя");
            System.out.println("2 - Найти пользователя по id");
            System.out.println("3 - Показать всех пользователей");
            System.out.println("4 - Обновить пользователя");
            System.out.println("5 - Удалить пользователя");
            System.out.println("0 - Выход");
            System.out.print("Выберите действие: ");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                HibernateUtil.shutdown();
                break;
            }

            switch (input) {
                case "1":
                    try {
                        System.out.print("Имя: ");
                        String name = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Возраст: ");
                        int age = Integer.parseInt(scanner.nextLine());

                        User user = new User(name, email, age);
                        userDao.save(user);
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат возраста");
                    }
                    break;

                case "2":
                    try {
                        System.out.print("Введите id: ");
                        Long id = Long.parseLong(scanner.nextLine());

                        User user = userDao.findById(id);
                        if (user != null) {
                            System.out.println(user);
                        } else {
                            System.out.println("Пользователь не найден");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат id");
                    }
                    break;

                case "3":
                    List<User> users = userDao.findAll();
                    for (User u : users) {
                        System.out.println(u);
                    }
                    break;

                case "4":
                    try {
                        System.out.print("Введите id для обновления: ");
                        Long id = Long.parseLong(scanner.nextLine());

                        User user = userDao.findById(id);
                        if (user == null) {
                            System.out.println("Пользователь не найден");
                            break;
                        }

                        System.out.print("Новое имя: ");
                        user.setName(scanner.nextLine());

                        System.out.print("Новый email: ");
                        user.setEmail(scanner.nextLine());

                        System.out.print("Новый возраст: ");
                        user.setAge(Integer.parseInt(scanner.nextLine()));

                        userDao.update(user);
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат числа");
                    }
                    break;

                case "5":
                    try {
                        System.out.print("Введите id для удаления: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        userDao.delete(id);
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат id");
                    }
                    break;

                default:
                    System.out.println("Неизвестная команда");
                    break;
            }
        }
    }
}
