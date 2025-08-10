# Insider QA Automation Case Study

A UI automation project built for the **Insider QA Engineer – Selenium Task**.  
It verifies critical user flows on [useinsider.com](https://useinsider.com) using **Java + Selenium + TestNG**, implemented with **TDD** and a clean **Page Object Model (POM)** design.

---

## ✅ Tech Stack

- **Language:** Java
- **Automation:** Selenium WebDriver
- **Test Framework:** TestNG
- **Reporting:** ExtentReports (HTML)
- **Design:** Page Object Model (POM), utilities & reusable methods

---

---

## 🧭 Scope & Scenarios (mapped to the case study)

1. **Home Page**
    - Open `https://useinsider.com/` and verify the page is loaded (URL check).

2. **Careers Navigation**
    - From **Company** menu select **Careers**.
    - On Careers page, verify **Locations**, **Teams**, and **Life at Insider** blocks are visible.

3. **QA Jobs Filtering**
    - Go to `https://useinsider.com/careers/quality-assurance/`.
    - Click **See all QA jobs**.
    - Filter by **Location:** *Istanbul, Turkiye* (site label may show *Turkey/Türkiye* depending on locale)  
      and **Department:** *Quality Assurance*.
    - Verify that a jobs list is present.

4. **Job Card Content**
    - For each job, validate:
        - **Department** contains *Quality Assurance*.
        - **Location** contains *Istanbul, Turkiye*.
        - **Position/Title** contains *Quality Assurance* (case‑insensitive).

5. **View Role Redirect**
    - Click each **View Role** button and assert redirection to the **Lever** application page.

> The suite avoids BDD by design (as required) and uses TDD with clear assertions and step logs.

---

## 🧪 How to Run

### Prerequisites
- JDK 17+ (project uses modern JDK; 21/23 supported as well)
- Maven 3.8+
- Chrome / chromedriver handled by Selenium Manager (no manual setup required)

### Setup
1. Clone the repo
   ```bash
   git clone <your-repo-url>
   cd <your-repo-folder>

📊 Reporting
•	ExtentReports generates an HTML report after each run:
test-output/ExtentReport_YYYYMMDD_HHmmss.html

## ✅ Design Notes

- **POM & Clean Code:** Page classes encapsulate locators and interactions; tests contain only scenario logic.
  Helpers in ReusableMethods provide explicit waits, scrolling, and small utilities.
- **Stability:** Custom dropdowns (Select2) are interacted with by clicking options, then waiting for AJAX‑loaded results, avoiding brittle Thread.sleep.
- **Driver Lifecycle:** Managed centrally in BaseTest via the singleton Driver utility.
- **No BD:** The suite follows TDD with clear, atomic assertions and readable step logs. 
