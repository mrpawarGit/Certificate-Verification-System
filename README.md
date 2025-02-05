# **Certificate Verification System**  

## **Overview**  
The **Certificate Verification System** is a blockchain-powered web application designed to provide **secure and tamper-proof** verification of academic certificates. It prevents fraud by leveraging blockchain for decentralized storage and verification. **QR Code functionality** allows easy scanning for instant certificate validation.  

## **Features**  
✅ **QR Code Generation** – Each certificate has a unique QR code for instant verification.  
✅ **Web-Based Interface** – User-friendly portal using JSP and Servlets.  
✅ **Database Integration** – Stores metadata in MySQL.  
✅ **User Authentication** – Secure login for admins and institutions.  

## **Tech Stack**  
- **Backend**: Java, JSP, Servlets  
- **Database**: MySQL  
- **Frontend**: HTML, CSS, JavaScript  
- **QR Code Generator**: ZXing (or similar Java QR code library)  

## **Installation & Setup**  
### **Prerequisites**  
- Java JDK (8 or above)  
- Apache Tomcat Server  
- MySQL Database  
- Blockchain Node (e.g., Ganache for Ethereum)  
- QR Code Generator Library (ZXing or an alternative)  

### **Steps to Run**  
1. Clone the repository:  
   ```sh
   git clone https://github.com/mrpawarGit/Certificate-Verification-System.git
   cd Certificate-Verification-System/Academic_certificate_verification_using_Blockchain
   ```
2. Set up the MySQL database and import the provided SQL file.  
3. Configure database credentials in the project.  
4. Deploy the project on Tomcat.  
5. Run the application and access it via the browser.  

## **Usage**  
1. **Admin/Institution**: Uploads certificates to the blockchain and generates a QR code.  
2. **Students**: View, download, and share their verified certificates with QR codes.  
3. **Employers**: Scan the QR code to verify certificate authenticity instantly.  

## **Future Enhancements**  
- Implement support for **multiple blockchains**.  
- Add **email notifications** for certificate issuance.  
- Improve **QR Code styling** with logos and branding.  
