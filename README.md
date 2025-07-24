# XFlipkart Search Test Automation

## 📝 Project Overview

**XFlipkart Search Test Automation** is a test automation project built to validate the **product search and filter functionality** on the Flipkart website. It automates the process of searching for products and retrieves the **count of items categorized by**:

- Star Ratings
- Price Ranges
- Number of Reviews

This suite is designed for web UI testing using **Selenium WebDriver**, written in **Java**, and structured using **TestNG**.

---

## ✅ Test Cases

### 🧪 testCase01
- Navigate to [www.flipkart.com](https://www.flipkart.com)
- Search for **"Washing Machine"**
- Sort results by **Popularity**
- Print the **count of items** with **ratings less than or equal to 4 stars**

---

### 🧪 testCase02
- Search for **"iPhone"**
- Print the **Title** and **Discount %** of items with **more than 17% discount**

---

### 🧪 testCase03
- Search for **"Coffee Mug"**
- Apply the filter: **4 stars and above**
- Print the **Title** and **Image URL** of the **top 5 items** with the **highest number of reviews**

---

## 🔧 Tech Stack

- **Language:** Java  
- **Automation Framework:** Selenium
- **Testing Framework:** TestNG  
- **Build Tool:** Gradle  
- **Browser:** Google Chrome

---

## 📦 Installation

Follow these steps to set up the project locally:

### 1. Clone the Repository

git clone https://github.com/NiviyaJ/FlipkartSearchAutomation.git
cd FlipkartSearchAutomation

### 2. Install Dependencies

Ensure **Java (JDK 11 or higher)** and **Gradle** are installed, then run:

gradle clean build

This will download all required dependencies and compile the test classes.

---

## 🌐 System Requirements

- Java 11 or later
- Gradle
- Google Chrome (latest)
- ChromeDriver (compatible with your Chrome version)

> Ensure that the ChromeDriver executable is available in your system `PATH`, or configure its location in the test setup.

---

## 🚀 Running the Tests

To execute all tests using Gradle, run:

./gradlew test

🛠️ Make Gradle Executable (Linux/macOS)

chmod +x gradlew
./gradlew test

🪟 Windows

gradlew.bat test

You can also run specific test classes using your IDE or with TestNG configurations if implemented.

## 📬 Author

Maintained by **[Niviya Joy]**  
GitHub: [https://github.com/NiviyaJ]
