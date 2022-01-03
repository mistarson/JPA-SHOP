package jpabook.jpashop.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "아이디는 필수입니다.")
    private String loginId;

    @NotEmpty(message = "패스워드는 필수입니다.")
    private String password;

    private String city;
    private String street;
    private String zipcode;
}
