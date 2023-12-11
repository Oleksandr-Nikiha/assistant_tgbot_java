package com.example.mydemobot.bot.constans;

public interface Response {
    String START_BOT = "Бот запущений.";
    String START_MENU = "Вітаю, %s! \nРадий познайомитися, я бот домашній помічник. \nБуду радий допомогти.";
    String GENERAL_MENU = "Ви в головному меню";
    String NOTHING_TODO = "Sorry you in incorrect state.\nOr bot given internal error\nPlease going in General Menu use command '/menu'";
    String BACK_MENU = "Ви повернулися до головного меню";
    String CURRENCY_MENU = "Ви в розділі Курс Валют \ud83d\udcb1 \nОберіть потрібну валюту.";
    String CURRENCY_INFO = "На поточний день курс складає %s UAH до 1 %s \nЗгідно офіційним даним з відкритого ресурсу НБУ.";
    String WEATHER_MENU = "Ви в розділі Погоди \uD83C\uDF0E \nОберіть місто яке вас цікавить.";
    String WEATHER_INFO = "\uD83C\uDF07 В місті %s - %s \uD83C\uDF07\n" +
            "\uD83C\uDF21\uFE0F Температура: %sC°\n" +
            "\uD83D\uDCA7 Вологість: %s%%\n" +
            "\uD83E\uDE90 Атмосферний тиск: \n%s мм рт. ст.\n" +
            "\uD83D\uDCA8 Швидкість вітру: \n%s км/г\n" +
            "\uD83D\uDCC5 Останнє оновлення: \n%s";
    String ACCOUNTING_START = "Ви в розділі Обліку фінансів.\nОберіть пункт який вас цікавить.";
    String SET_INCOME = "Вкажіть суму доходу яку ви хочете зафіксувати. Бажано в форматі 123.55";
    String SET_SPEND = "Вкажіть суму витрати яку ви хочете зафіксувати. Бажано в форматі 123.55";
    String SET_ANN = "Вкажіть додаткову інформацію.\nНапр. \"Надходження заробітньої плати\" або \"Купівля продуктів харчування\"";
    String SUCCES_SET = "Дякую, я успішно зафіксував інформацію.\nДата: %s\nСума: %s\nКатегорія: %s\nНотаток: %s";
    String APPROVE_SET = "Я підтвердив поточний запис.\nОберіть бажаний пункт для нового запису або перегляду статистики";
    String REJECT_SET = "Я видалив поточний запис.\nОберіть бажаний пункт для нового запису або перегляду статистики";
    String STATISTIC_MENU = "";
    String OOOPS = "Йойкс!\nВибачаюсь але щось пішло не так. Повертаю вас до головного меню.";
}
