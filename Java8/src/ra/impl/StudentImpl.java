package ra.impl;

import ra.entity.Student;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class StudentImpl {
    static List<Student> studentList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        readDataFromFile();
        do {
            System.out.println("*****************************MENU************************");
            System.out.println("1. Nhập thông tin các sinh viên");
            System.out.println("2. Tính tuổi các sinh viên");
            System.out.println("3. Tính điểm trung bình và xếp loại sinh viên");
            System.out.println("4. Sắp xếp sinh viên theo tuổi tăng dần");
            System.out.println("5. Thống kê sinh viên theo xếp loại sinh viên");
            System.out.println("6. Cập nhật thông tin sinh viên theo mã sinh viên");
            System.out.println("7. Tìm kiếm sinh viên theo tên sinh viên");
            System.out.println("8. Thoát");
            System.out.println("Sự lựa chọn của bạn là: ");
            boolean checkChoice = true;
            int choice = 0;
            do {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    checkChoice = false;
                } catch (NumberFormatException ex) {
                    System.err.println("Sự lựa chọn phải là số nguyên, vui lòng nhập lại");
                } catch (Exception ex1) {
                    System.err.println("Xảy ra lỗi trong quá trình xử lý, vui lòng nhập lại");
                }
            } while (checkChoice);
            switch (choice) {
                case 1:
                    StudentImpl.inputStudentList();
                    break;
                case 2:
                    studentList.forEach(Student::calAge);
                    System.out.println("Đã tính xong tuổi các sinh viên");
                    break;
                case 3:
                    studentList.forEach(Student::calAvgMark_Rank);
                    System.out.println("Đã tính xong điểm trung bình và xếp loại sinh viên");
                    break;
                case 4:
                    StudentImpl.sortAge();
                    break;
                case 5:
                    StudentImpl.thongKe();
                    break;
                case 6:
                    StudentImpl.updateStudent();
                    break;
                case 7:
                    StudentImpl.searchStudentName();
                    break;
                case 8:
                    writeDataToFile();
                    System.exit(0);
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-8");
            }
        } while (true);
    }
    public static void inputStudentList(){
        Student studentNew = new Student();
        studentNew.inputData(scanner, studentList);
        studentList.add(studentNew);
        System.out.println("Đã thêm thông tin sinh viên thành công");
    }
    public static void sortAge(){
        studentList.sort(Comparator.comparing(Student::getAge));
        System.out.println("Đã sắp xếp xong sinh viên theo tuổi tăng dần");
    }
    public static void thongKe(){
        System.out.println("Sinh viên xếp loại yếu là: "+ (int) studentList.stream().filter(student -> student.getRank().equals("Yếu")).count()+" sinh viên");
        System.out.println("Sinh viên xếp loại trung bình là: "+ (int) studentList.stream().filter(student -> student.getRank().equals("Trung bình")).count()+" sinh viên");
        System.out.println("Sinh viên xếp loại khá là: "+ (int) studentList.stream().filter(student -> student.getRank().equals("Khá")).count()+" sinh viên");
        System.out.println("Sinh viên xếp loại giỏi là: "+ (int) studentList.stream().filter(student -> student.getRank().equals("Giỏi")).count()+" sinh viên");
        System.out.println("Sinh viên xếp loại xuất sắc là: "+ (int) studentList.stream().filter(student -> student.getRank().equals("Xuất sắc")).count()+" sinh viên");
    }
    public static int getIndexById(String studentId) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentId().equals(studentId)) {
                return i;
            }
        }
        return -1;
    }

    public static void updateStudent() {
        System.out.println("Nhập vào ID sinh viên muốn cập nhật");
        boolean checkUpdateId = true;
        do {
            String updateStudentId = scanner.nextLine();
            int indexUpdate = getIndexById(updateStudentId);
            if (indexUpdate!= -1){
                for (int i = 0; i < studentList.size(); i++) {
                    if (studentList.get(i).getStudentId().equals(updateStudentId)){
                        // Tiến hành update
                        System.out.println("Nhập vào tên sinh viên muốn cập nhật");

                        boolean checkUpdateName = true;
                        do {
                            String updateName = scanner.nextLine();
                            if (indexUpdate >= 0) {
                                if (updateName.length() >= 10 && updateName.length() <= 50) {
                                    studentList.get(indexUpdate).setStudentName(updateName);
                                    checkUpdateName = false;
                                } else {
                                    System.err.println("Tên sinh viên phải có từ 10-50 ký tự");
                                }
                            } else {
                                System.err.println("Sinh viên không tồn tại, vui lòng nhập lại");
                            }

                        } while (checkUpdateName);
                        System.out.println("Nhập vào ngày tháng năm sinh muốn cập nhật");
                        boolean checkUpdateBirthday = true;
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                        do {
                            try {
                                Date updateBirthDay = sdf1.parse(scanner.nextLine());
                                Calendar cal1 = Calendar.getInstance();
                                cal1.setTime(updateBirthDay);
                                if (cal1.get(Calendar.YEAR) < 2005) {
                                    studentList.get(indexUpdate).setBirthday(updateBirthDay);
                                    checkUpdateBirthday = false;
                                } else {
                                    System.err.println("Năm sinh phải trước năm 2005, vui lòng nhập lại");
                                }
                            } catch (ParseException ex1) {
                                System.err.println("Ngày sinh phải có định dạng dd/MM/yyyy, vui lòng nhập lại");
                            } catch (Exception ex) {
                                System.err.println("Xảy ra lỗi không xác định, vui lòng liên hệ với hệ thống");
                            }
                        } while (checkUpdateBirthday);
                        System.out.println("Nhập giới tính muốn cập nhật");
                        boolean checkUpdateSex = true;
                        do {
                            String updateSex = scanner.nextLine();
                            if (updateSex.equalsIgnoreCase("true") || updateSex.equalsIgnoreCase("false")){
                                studentList.get(indexUpdate).setSex(Boolean.parseBoolean(updateSex));
                                checkUpdateSex = false;
                            }else {
                                System.err.println("Dữ liệu không phải kiểu Boolean, vui lòng nhập lại");
                            }
                        }while (checkUpdateSex);
                        System.out.println("Điểm html muốn cập nhập là");
                        boolean checkUpdateHtml = true;
                        do {
                            float updateHtml = Float.parseFloat(scanner.nextLine());
                            if (updateHtml>=0 && updateHtml<=10){
                                studentList.get(indexUpdate).setHtmlMark(updateHtml);
                                checkUpdateHtml= false;
                            }else {
                                System.err.println("Điểm phải có giá trị từ 0-10");
                            }
                        }while (checkUpdateHtml);
                        System.out.println("Điểm css muốn cập nhập là");
                        boolean checkUpdateCss = true;
                        do {
                            float updateCss = Float.parseFloat(scanner.nextLine());
                            if (updateCss>=0 && updateCss<=10){
                                studentList.get(indexUpdate).setCssMark(updateCss);
                                checkUpdateCss= false;
                            }else {
                                System.err.println("Điểm phải có giá trị từ 0-10");
                            }
                        }while (checkUpdateCss);
                        System.out.println("Điểm javascript muốn cập nhập là");
                        boolean checkUpdateJavascript = true;
                        do {
                            float updateJavascript = Float.parseFloat(scanner.nextLine());
                            if (updateJavascript>=0 && updateJavascript<=10){
                                studentList.get(indexUpdate).setJavascriptMark(updateJavascript);
                                checkUpdateJavascript= false;
                            }else {
                                System.err.println("Điểm phải có giá trị từ 0-10");
                            }
                        }while (checkUpdateJavascript);
                        System.out.println("Đã cập nhật sinh viên thành công");
                        checkUpdateId = false;
                    }
                }
            }else {
                System.err.println("Mã sinh viên không tồn tại, vui lòng nhập lại");
            }
        } while (checkUpdateId) ;
    }
    public static void searchStudentName(){
        System.out.println("Nhập vào tên sinh viên muốn tìm kiếm");
        String name = scanner.nextLine();
        boolean checkName = false;
        System.out.println("THÔNG TIN SINH VIÊN");
        for (Student stu: studentList) {
            if (stu.getStudentName().toLowerCase().contains(name.toLowerCase())){
                stu.displayData();
                checkName = true;
            }
        }
        if (!checkName){
            System.err.println("Không có sinh viên nào được tìm thấy");
        }
    }
    public static void readDataFromFile() {
        File file = new File("studentList.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            if (ois.readObject() != null) {
                studentList = (List<Student>) ois.readObject();
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Không tồn tại file");
        } catch (IOException ex1) {
            System.err.println("Lỗi IO");
        } catch (Exception ex2) {
            System.err.println("Lớp không tồn tại");
        } finally {
            try {


                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ex) {
                System.err.println("Lớp không tồn tại");
            } catch (Exception ex1) {
                System.err.println("Đã xảy ra lỗi trong qua trình xử lý");
            }
        }
    }
    public static void writeDataToFile(){
        File file = new File("listStudent.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(studentList);
            oos.flush();
        } catch (FileNotFoundException e) {
            System.err.println("Lỗi runtime");
        } catch (IOException e) {
            System.err.println("Lỗi IO");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("Lỗi runtime");
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    System.err.println("Lỗi IO");
                }
            }
        }
    }
}
