"# Flipkart_Automation" 

📋 Overview

A comprehensive end-to-end automation framework for testing Flipkart's web application. This project validates e-commerce workflows, including product search, filters, cart operations, order placement, and broken link checks. Built with Selenium WebDriver, Java, TestNG, and follows POM design principles.

🚀 Features
Current Functionalities
E-Commerce Automation

Filter & Sorting

Validate product filters (brand, price, rating)

Sort products by relevance, price, and popularity

Product Search & Details

Search products by name

View product details and reviews

Navigate image galleries

Cart & Wishlist

Add, update, and remove items from cart

Apply coupon codes

Manage wishlist and move items to cart

Order Flow

Complete checkout process

Select delivery address and payment method

Validate order confirmation and order history

Broken Link Checks

Identify broken or inactive links across Flipkart pages

Test Automation Features

POM Design Pattern

Reusable utility classes

Automated test generation

Cross-browser testing (Chrome & Firefox)

Screenshots on failure

Detailed HTML reports

CI/CD integration

🛠️ Technology Stack

Java (Selenium WebDriver, TestNG)

Maven (Project management & build)

ExtentReports (Reporting)

WebDriverManager (Driver management)

Git (Version control)

📁 Project Structure
Flipkart_Automation/
│
├── src/
│   ├── main/java/
│   │   ├── pages/          # Page Objects (HomePage, LoginPage, ProductPage, CartPage, CheckoutPage)
│   │   ├── utils/          # Utility classes (DriverFactory, WaitHelper, ScreenshotUtil)
│   │   └── base/           # Base classes (BasePage, BaseTest)
│   │
│   └── test/java/
│       ├── tests/          # Test classes (FilterTest, OrderFlowTest, BrokenLinksTest)
│       └── listeners/      # TestNG Listeners and Retry Analyzer
│
├── resources/
│   ├── config/             # config.properties, test data Excel
│   ├── drivers/            # WebDriver executables
│   └── testng.xml
│
├── reports/                # TestNG & ExtentReports HTML reports
├── screenshots/            # Failed test screenshots
├── logs/                   # Automation logs
├── pom.xml                 # Maven configuration
├── .gitignore
└── README.md

⚙️ Setup Instructions
Prerequisites

Java JDK 11+

Maven 3.6+

Chrome/Firefox installed

IDE (IntelliJ IDEA / Eclipse)

Installation
git clone https://github.com/Dibya54/Flipkart_Automation.git
cd Flipkart_Automation
mvn clean install

Run Tests

Run all tests:

mvn clean test


Run specific test class:

mvn test -Dtest=FilterTest


Run with browser parameter:

mvn test -Dbrowser=chrome

📊 Test Reports

HTML reports: reports/ExtentReport.html

Screenshots: screenshots/

TestNG reports: test-output/index.html

🧪 Test Scenarios Covered

✅ Filter & sort validation

✅ Product search accuracy

✅ Cart operations and coupon application

✅ Checkout and order flow

✅ Broken link identification

🔮 Future Enhancements

Complete Flight Booking automation

CI/CD pipeline setup (GitHub Actions / Jenkins)

Mobile app automation (Appium)

Advanced reporting (Allure)

Database validation and API testing

🤝 Contributing

Fork the repository

Create a new feature branch

Commit changes

Push and create a Pull Request

📞 Contact

Developer: Dibyajyoti Roy
GitHub: @Dibya54

Repository: Flipkart_Automation

⚠️ Disclaimer

This project is for educational purposes and not affiliated with Flipkart. Ensure compliance with Flipkart’s Terms of Service when running automated tests.
