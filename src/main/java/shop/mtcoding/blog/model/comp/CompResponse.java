package shop.mtcoding.blog.model.comp;

import lombok.Builder;
import lombok.Data;
import shop.mtcoding.blog.model.apply.Apply;
import shop.mtcoding.blog.model.jobs.Jobs;
import shop.mtcoding.blog.model.resume.Resume;
import shop.mtcoding.blog.model.skill.Skill;
import shop.mtcoding.blog.model.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompResponse {

    @Data
    public static class CompUpdateDTO {
        private Integer id;
        private String myName;
        private String compName;
        private String phone;
        private String businessNumber;
        private String homepage;
        private LocalDate birth;
        private String address;

        public CompUpdateDTO(User user) {
            this.id = user.getId();
            this.myName = user.getMyName();
            this.compName = user.getCompName();
            this.phone = user.getPhone();
            this.businessNumber = user.getBusinessNumber();
            this.homepage = user.getHomepage();
            this.birth = user.getBirth();
            this.address = user.getAddress();
        }
    }

    @Data
    public static class CompManageDTO {
        private Integer jobsCount;
        private Integer applicantCount;
        private Integer noRespCount;
        private List<JobsDTO> jobsDTOList;

        @Builder
        public CompManageDTO(Integer jobsCount,Integer applicantCount,Integer noRespCount, List<Jobs> jobsDTOList) {
            this.jobsCount = jobsCount;
            this.applicantCount = applicantCount;
            this.noRespCount = noRespCount;
            this.jobsDTOList = jobsDTOList.stream().map(jobs -> {
                return new JobsDTO(jobs, jobs.getSkillList());
            }).toList();
        }

        @Data
        public class JobsDTO {
            private String title;
            private String career;
            private String edu;
            private String area;
            private Integer jobsId;
            private List<SkillDTO> skillList;

            public JobsDTO(Jobs jobs, List<Skill> skillList) {
                this.title = jobs.getTitle();
                this.career = jobs.getCareer();
                this.edu = jobs.getEdu();
                this.area = jobs.getArea();
                this.jobsId = jobs.getId();
                this.skillList = skillList.stream().map(SkillDTO::new).toList();
            }
        }

        @Data
        public class SkillDTO {
            private Integer id;
            private String name;

            public SkillDTO(Skill skill) {
                this.id = skill.getId();
                this.name = skill.getName();
            }
        }
    }


    @Data
    public static class ComphomeDTO {
        private Integer id;
        private String title;
        private String task;
        private String career;
        private Integer jobsId;
        private List<SkillDTO> skillList;

        @Builder
        public ComphomeDTO(Jobs jobs, List<Skill> skillList) {
            this.title = jobs.getTitle();
            this.task = jobs.getTask();
            this.career = jobs.getCareer();
            this.jobsId = jobs.getId();
            this.skillList = skillList.stream().map(skill -> {
                return new SkillDTO(skill);
            }).collect(Collectors.toList());
        }
    }


    @Data // Resume랑 User랑 SkillList랑 Apply를 담을 DTO
    public static class RusaDTO {
        private Integer id;
        // user
        private String myName;
        // resume
        private Integer resumeId;
        private String title;
        private String career;
        private List<SkillDTO> skillList;
        // apply
        private Integer jobsId;
        private Boolean isPass;
        private Boolean isApply;

        @Builder
        public RusaDTO(Integer id, User user, Resume resume, Apply apply) {
            this.id = id;
            this.myName = user.getMyName();
            this.resumeId = resume.getId();
            this.title = resume.getTitle();
            this.career = resume.getCareer();
            this.jobsId = apply.getJobs().getId();
            this.skillList = resume.getSkillList().stream()
                    .map(SkillDTO::new)
                    .collect(Collectors.toList());

            // 1. 지원x 2.지원중 3.합격 4.불합격
            if (apply.getIsPass().equals("1")) {
                this.isApply = false;
            } else if (apply.getIsPass().equals("2")) {
                this.isApply = true;
            } else if (apply.getIsPass().equals("3")) {
                this.isApply = true;
                this.isPass = true;
            } else if (apply.getIsPass().equals("4")) {
                this.isApply = true;
                this.isPass = false;
            }
        }
    }


    @Data
    public static class JobsSkillDTO {
        private Integer id;
        private String compName;
        private String title;
        private String career;
        private String area;
        private LocalDate deadline;
        private String imgFileName;
        private List<SkillDTO> skillList = new ArrayList<>();

        @Builder
        public JobsSkillDTO(Jobs jobs, User user, List<Skill> skillList) {
            this.id = jobs.getId();
            this.compName = user.getCompName();
            this.title = jobs.getTitle();
            this.career = jobs.getCareer();
            this.area = jobs.getArea();
            this.deadline = jobs.getDeadline();
            this.imgFileName = user.getImgFileName();
            this.skillList = skillList.stream().map(skill -> new SkillDTO(skill)).collect(Collectors.toList());

        }
    }


    @Data
    public static class ResumeUserSkillDTO {
        //resume
        private Integer id;
        private String title;
        private String edu;
        private String career;
        private String area;
        //user
        private String myName;
        private Integer userId;
        private Integer jobsId;
        private String imgFileName;
        private List<SkillDTO> skillList = new ArrayList<>();

        @Builder
        public ResumeUserSkillDTO(Resume resume, User user, List<Skill> skillList) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.edu = resume.getEdu();
            this.career = resume.getCareer();
            this.area = resume.getArea();
            this.myName = user.getMyName();
            this.userId = user.getId();
            this.imgFileName = user.getImgFileName();
            this.skillList = skillList.stream()
                    .map(skill -> new SkillDTO(skill))
                    .collect(Collectors.toList());
            ;
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

}
