package model;

import exceptions.ExceptionCode;
import exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class Customer {
    private String name;
    private String surname;
    private Integer age;
    private String email;

    public Customer(CustomerBuilder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.age = builder.age;
        this.email = builder.email;
    }

    public static CustomerBuilder builder(){
        return new CustomerBuilder();
    }

    public static class CustomerBuilder{
        private String name;
        private String surname;
        private Integer age;
        private String email;

        public CustomerBuilder name(String name){
            try{
                if(name==null){
                    throw new MyException(ExceptionCode.BUILDER, "NAME IS NULL ");
                }
                if(!name.matches("[A-Z ]*")){
                    throw new MyException(ExceptionCode.BUILDER, "INCORRECT FORMAT OF STRING");
                }
                this.name=name;

                return this;
            }catch (MyException e){
                throw new MyException(ExceptionCode.BUILDER,"CUSTOMER BUILDER NAME EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public CustomerBuilder surname(String surname){
            try{
                if(surname==null){
                    throw new MyException(ExceptionCode.BUILDER, "SURNAME IS NULL ");
                }
                if(!surname.matches("[A-Z ]*")){
                    throw new MyException(ExceptionCode.BUILDER, "INCORRECT FORMAT OF STRING");
                }
                this.surname=surname;

                return this;
            }catch (MyException e){
                throw new MyException(ExceptionCode.BUILDER,"CUSTOMER BUILDER SURNAME EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public CustomerBuilder age(Integer age){
            try{
                if(age==null){
                    throw new MyException(ExceptionCode.BUILDER, "AGE IS NULL");
                }
                if(age<18){
                    throw new MyException(ExceptionCode.BUILDER, "AGE UNDER 18");
                }
                this.age=age;

                return this;
            }catch (MyException e){
                throw new MyException(ExceptionCode.BUILDER,"CUSTOMER BUILDER SURNAME EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public CustomerBuilder email(String email){
            try{
                if(email==null){
                    throw new MyException(ExceptionCode.BUILDER, "EMAIL IS NULL ");
                }
                if(!email.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")){
                    throw new MyException(ExceptionCode.BUILDER, "INCORRECT FORMAT OF EMAIL");
                }
                this.email=email;

                return this;
            }catch (MyException e){
                throw new MyException(ExceptionCode.BUILDER,"CUSTOMER BUILDER EMAIL EXCEPTION: "
                        + e.getExceptionInfo().getMessage());
            }
        }
        public Customer build() {

            return new Customer(this);
        }
    }
}
