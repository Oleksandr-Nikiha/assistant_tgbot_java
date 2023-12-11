# MyJavaBot

MyJavaBot - це Telegram-бот, розроблений на основі Spring Boot, який надає інформацію про валютні курси та погоду.

## Огляд

Цей бот отримує дані про:
1. Валютні курси з веб-сайту Національного банку України та надає користувачам інформацію про курси обміну валют за їхнім кодом.

2. Погоду з веб-сайту AccuWeather та надає користувачам інформацію про погоду за назвою міста.

## Встановлення

1. Клонуйте репозиторій: git clone https://github.com/LuckerDie/MyJavaBot.git

2. Відкрийте проект у вашому IDE та налаштуйте конфігурацію залежно від вашого середовища.

3. Додайте наступні змінні:
    1. bot.token
    2. bot.username
    3. nbu.url
    4. weather.token
    5. weather.url
    6. weathr.parsing_url

4. Випустіть сертифікат для firebase, та покладіть в resources

5. Додайте зміну firebase.filename - це назва сертифікату який ви випустили раніше. 

6. Запустіть додаток: mvn spring-boot:run


## Використання

Після запуску бота, ви можете використовувати його.

## Внесок

Якщо ви хочете внести внесок у розвиток проекту, відкрийте pull request або створіть issue у цьому репозиторії.

## Ліцензія

Цей проект розповсюджується під ліцензією [MIT License](LICENSE).

---

**Зауваження:** Цей проект призначений для навчальних цілей та як приклад роботи з Telegram API та Spring Boot. Не рекомендується використовувати його для комерційних цілей без належного аналізу та модифікацій.