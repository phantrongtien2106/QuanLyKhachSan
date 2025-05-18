# Cài đặt thư viện iTextPDF và DatePicker

## Thư viện đã được tải xuống

Các thư viện sau đã được tải xuống và đặt trong thư mục `lib`:

- `itextpdf-5.5.13.3.jar`: Thư viện iTextPDF để tạo và xử lý file PDF
- `LGoodDatePicker-11.2.1.jar`: Thư viện DatePicker để chọn ngày tháng trong giao diện

## Cách biên dịch và chạy với thư viện

Để dễ dàng biên dịch và chạy các file Java với thư viện, bạn có thể sử dụng các file batch đã được tạo sẵn:

### Biên dịch file Java với thư viện

```
compile_with_libs.bat src/TenFile.java
```

Ví dụ:
```
compile_with_libs.bat src/TestBothLibraries.java
```

### Chạy file Java với thư viện

```
run_with_libs.bat TenClass
```

Ví dụ:
```
run_with_libs.bat TestBothLibraries
```

## Cách thêm thư viện vào IntelliJ IDEA

1. Mở IntelliJ IDEA và mở dự án của bạn
2. Chọn menu **File > Project Structure** (hoặc nhấn Ctrl+Alt+Shift+S)
3. Chọn **Libraries** trong phần **Project Settings**
4. Nhấn nút **+** và chọn **Java**
5. Điều hướng đến thư mục `lib` trong dự án của bạn
6. Chọn cả hai file JAR: `itextpdf-5.5.13.3.jar` và `LGoodDatePicker-11.2.1.jar`
7. Nhấn **OK** để thêm thư viện
8. Chọn module mà bạn muốn thêm thư viện (thường là module chính của dự án)
9. Nhấn **OK** để xác nhận
10. Nhấn **Apply** và **OK** để lưu thay đổi

## Cách thêm thư viện vào Eclipse

1. Mở Eclipse và mở dự án của bạn
2. Nhấp chuột phải vào dự án trong Project Explorer
3. Chọn **Build Path > Configure Build Path**
4. Chọn tab **Libraries**
5. Nhấn **Add JARs** nếu bạn muốn thêm JAR từ dự án, hoặc **Add External JARs** nếu bạn muốn thêm JAR từ bên ngoài dự án
6. Điều hướng đến thư mục `lib` và chọn cả hai file JAR
7. Nhấn **OK** để thêm thư viện
8. Nhấn **Apply and Close** để lưu thay đổi

## Cách sử dụng DatePicker trong code

```java
import com.github.lgooddatepicker.components.DatePicker;

// Tạo DatePicker
DatePicker datePicker = new DatePicker();

// Đặt ngày hiện tại
datePicker.setDate(LocalDate.now());

// Thêm vào JPanel hoặc JFrame
panel.add(datePicker);

// Lấy ngày đã chọn
LocalDate selectedDate = datePicker.getDate();
```

## Cách sử dụng iTextPDF trong code

```java
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

// Tạo document
Document document = new Document();
PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
document.open();

// Thêm nội dung
document.add(new Paragraph("Hello World!"));

// Đóng document
document.close();
```

## Kiểm tra cài đặt

Để kiểm tra xem thư viện đã được cài đặt đúng cách, bạn có thể chạy file `TestLibraries.java` đã được tạo trong dự án. File này sẽ tạo một cửa sổ đơn giản với DatePicker và một nút để tạo file PDF.
