package shop.mtcoding.blog.model.jobs;

import lombok.Builder;
import lombok.Data;
import shop.mtcoding.blog.model.resume.Resume;
import shop.mtcoding.blog.model.skill.Skill;
import shop.mtcoding.blog.model.skill.SkillResponse;
import shop.mtcoding.blog.model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobsResponse {

    @Data
    public static class JobsDetailDTO {
        //jobs
        private Integer id;
        private String title;
        private String area;
        private String edu;
        private String career;
        private String content;
//        private boolean isOwner;

        //user
        private Integer userId;
        private String compName;
        private String phone;
        private String address;
        private String homepage;

        //skill
        private List<SkillDTO2> skills = new ArrayList<>();

        public JobsDetailDTO(Jobs jobs, User user) {
            this.id = jobs.getId();
            this.title = jobs.getTitle();
            this.area = jobs.getArea();
            this.edu = jobs.getEdu();
            this.career = jobs.getCareer();
            this.content = jobs.getContent();
            this.userId = jobs.getUser().getId();
            this.compName = jobs.getUser().getCompName();
            this.phone = jobs.getUser().getPhone();
            this.address = jobs.getUser().getAddress();
            this.homepage = jobs.getUser().getHomepage();
            this.skills = jobs.getSkillList().stream().map(skill ->
                    new SkillDTO2(skill)).collect(Collectors.toList());

//                    skills.stream().map(skill ->
//                    new SkillDTO2(jobs, skill)).collect(Collectors.toList());
        }

        @Data
        public class SkillDTO2 {
            private Integer id;
            private String name;

            public SkillDTO2(Skill skill) {
                this.id = skill.getId();
                this.name = skill.getName();
            }
        }

    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String area;
        private String edu;
        private String career;
        private String content;
        private UserDetailDTO user;
        private List<SkillDTO> skillList;
        private Boolean isOwner;

        @Builder
        public DetailDTO(Jobs jobs, User user, List<Skill> skillList) {
            this.id = jobs.getId();
            this.title = jobs.getTitle();
            this.area = jobs.getArea();
            this.edu = jobs.getEdu();
            this.career = jobs.getCareer();
            this.content = jobs.getContent();
            this.user = new UserDetailDTO(user);
            this.skillList = skillList.stream()
                    .map(skill -> new SkillDTO(skill))
                    .collect(Collectors.toList());
        }
    }

    @Data
    public static class UserDetailDTO {
        private Integer id;
        private String compName;
        private String phone;
        private String address;
        private String homepage;
        private String imgFileName;
        private Integer role;
        @Builder
        public UserDetailDTO(User user) {
            this.id = user.getId();
            this.compName = user.getCompName();
            this.phone = user.getPhone();
            this.address = user.getAddress();
            this.homepage = user.getHomepage();
            this.imgFileName = user.getImgFileName();
            this.role = user.getRole();
        }
    }


    // 공고 리스트를 뿌려야 되는 곳에 '회사이름''공고필요기술''공고정보'를 뿌릴 수 있는 DTO
    @Data
    public static class ListDTO { //이 DTO는 (/jobs/info) 에 사용된다.
        private Integer id;
        private String title;
        private String career;
        private String area;
        private LocalDate deadline;
        private String imgFileName;
        private String compName;
        private List<SkillDTOs> skills;

        @Builder
        public ListDTO(Jobs jobs, User user, List<Skill> skills) {
            this.id = jobs.getId();
            this.title = jobs.getTitle();
            this.career = jobs.getCareer();
            this.area = jobs.getArea();
            this.deadline = jobs.getDeadline();
            this.imgFileName = user.getImgFileName();
            this.compName = user.getCompName();

            this.skills = skills.stream()
                    .map(skill -> new SkillDTOs(skill))
                    .collect(Collectors.toList());
        }

        @Data
        public static class SkillDTOs {
            private String name;

            public SkillDTOs(Skill skill) {
                this.name = skill.getName();
            }
        }
    }


    @Data
    public static class ApplyResumeListDTO {
        private Integer id;
        private Integer jobsId;
        private String myName;
        private String title;
        private String isPass;
        private String state;
        private String career;
        private List<SkillDTO> skills;

        @Builder
        public ApplyResumeListDTO(Resume resume, Jobs jobs, String myName, String isPass, List<Skill> skills) {
            this.id = resume.getId();
            this.jobsId = jobs.getId();
            this.title = resume.getTitle();
            this.isPass = isPass;
            this.career = resume.getCareer();
            this.myName = myName;
            this.skills = skills.stream()
                    .map(skill -> new SkillDTO(skill))
                    .collect(Collectors.toList());

            if (this.isPass.equals("2")) {
                this.state = "지원중";
            } else if (this.isPass.equals("3")) {
                this.state = "합격";
            } else if (this.isPass.equals("4")) {
                this.state = "불합격";
            }
        }
    }

    @Data
    public static class UserDTO {
        private Integer id;
        private String compName;
        private String myName;
        private String imgFileName;

        public UserDTO(User user) {
            this.id = user.getId();
            this.compName = user.getCompName();
            this.myName = user.getMyName();
            this.imgFileName = user.getImgFileName();
        }
    }

    @Data
    public static class SkillDTO {
        private Integer id;
        private String name;
        private String color;

        public SkillDTO(Skill skill) {
            this.id = skill.getId();
            this.name = skill.getName();

            // 혹시 언어 추가할게 있으면 else if랑 컬러, 같은 양식 맞춰서 추가가능
            if (this.name.equals("Jquery")) {
                this.color = "badge badge-pill bg-primary";
            } else if (this.name.equals("JavaScript")) {
                this.color = "badge badge-pill bg-secondary";
            } else if (this.name.equals("Spring")) {
                this.color = "badge badge-pill bg-success";
            } else if (this.name.equals("HTML/CSS")) {
                this.color = "badge badge-pill bg-danger";
            } else if (this.name.equals("JSP")) {
                this.color = "badge badge-pill bg-warning";
            } else if (this.name.equals("Java")) {
                this.color = "badge badge-pill bg-info";
            } else if (this.name.equals("React")) {
                this.color = "badge badge-pill bg-dark";
            } else if (this.name.equals("Vue.js")) {
                this.color = "badge badge-pill bg-Indigo";
            } else if (this.name.equals("Oracle")) {
                this.color = "badge badge-pill bg-brown";
            } else if (this.name.equals("MySql")) {
                this.color = "badge badge-pill bg-purple";
            }
            // 추가 양식
            // else if (this.name.equals("언어")){
            //      this.color = "badge 컬러 " ;

        }
    }

    @Data
    public static class JobUpdateDTO {
        private Integer id;
        private String compName;
        private String phone;
        private String businessNumber;
        private String homepage;
        private String title;
        private String edu;
        private String career;
        private String content;
        private String area;
        private LocalDate deadLine;
        private String task;
        private List<SkillDTOs> skills;

        @Builder
        public JobUpdateDTO(Jobs jobs, User user, List<Skill> skills) {
            this.id = jobs.getId();
            this.compName = user.getCompName();
            this.phone = user.getPhone();
            this.businessNumber = user.getBusinessNumber();
            this.homepage = user.getHomepage();
            this.title = jobs.getTitle();
            this.edu = jobs.getEdu();
            this.career = jobs.getCareer();
            this.content = jobs.getContent();
            this.area = jobs.getArea();
            this.deadLine = jobs.getDeadline();
            this.task = jobs.getTask();
            this.skills = skills.stream()
                    .map(skill -> new SkillDTOs(skill))
                    .collect(Collectors.toList());
        }

        @Data
        public static class SkillDTOs {
            private String name;

            public SkillDTOs(Skill skill) {
                this.name = skill.getName();
            }
        }
    }

    @Data
    public static class saveDTO {
        private Integer id;
        private String compName;
        private String phone;
        private String businessNumber;
        private String homepage;
        private String title;
        private String edu;
        private String career;
        private String content;
        private String area;
        private LocalDate deadLine;
        private String task;
        private List<String> skill;
    }

    @Data
    @Builder
    public static class searchDTO {
        private String area;
        private String skill;
        private String career;
    }


}


