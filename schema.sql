-- This schema is designed for an online examination system.
-- It includes tables for students, instructors, exams, questions,
-- and the relationships between them.

--
-- Table structure for table `student`
--
-- This table stores personal and academic details of the students.
--

CREATE TABLE `student` (
  `sid` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `dob` DATE DEFAULT NULL,
  `gender` CHAR(1) DEFAULT NULL,
  `address` TEXT,
  `pincode` VARCHAR(10) DEFAULT NULL,
  `phone_no` VARCHAR(15) DEFAULT NULL,
  `email_id` VARCHAR(100) DEFAULT NULL,
  `institute_name` VARCHAR(100) DEFAULT NULL,
  `branch` VARCHAR(50) DEFAULT NULL,
  `semester` VARCHAR(10) DEFAULT NULL,
  `year` VARCHAR(10) DEFAULT NULL,
  `scholar_no` VARCHAR(20) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `recovery_question` VARCHAR(255) DEFAULT NULL,
  `answer` VARCHAR(255) DEFAULT NULL,
  `image` BLOB,
  `login_id` INT DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ;

--
-- Table structure for table `instructor`
--
-- This table stores information about the instructors.
--

CREATE TABLE `instructor` (
  `iid` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `dob` DATE DEFAULT NULL,
  `gender` CHAR(1) DEFAULT NULL,
  `address` TEXT,
  `pincode` VARCHAR(10) DEFAULT NULL,
  `phone_no` VARCHAR(15) DEFAULT NULL,
  `email_id` VARCHAR(100) DEFAULT NULL,
  `institute_name` VARCHAR(100) DEFAULT NULL,
  `department` VARCHAR(50) DEFAULT NULL,
  `instructor_id` VARCHAR(20) DEFAULT NULL,
  `password` VARCHAR(255) DEFAULT NULL,
  `recovery_question` VARCHAR(255) DEFAULT NULL,
  `answer` VARCHAR(255) DEFAULT NULL,
  `image` BLOB,
  PRIMARY KEY (`iid`)
) ;

--
-- Table structure for table `exam`
--
-- This table contains the core details of an examination.
--

CREATE TABLE `exam` (
  `iid` INT NOT NULL,
  `eid` INT NOT NULL,
  `ename` VARCHAR(100) NOT NULL,
  `edate_time` DATETIME DEFAULT NULL,
  `eduration` INT DEFAULT NULL,
  `group_id` INT DEFAULT NULL,
  PRIMARY KEY (`eid`),
  KEY `iid` (`iid`),
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`iid`) REFERENCES `instructor` (`iid`) ON DELETE CASCADE
) ;

--
-- Table structure for table `question`
--
-- This table stores all the questions and their multiple-choice answers.
--

CREATE TABLE `question` (
  `qid` INT NOT NULL,
  `qquestion` TEXT NOT NULL,
  `qop1` VARCHAR(255) DEFAULT NULL,
  `qop2` VARCHAR(255) DEFAULT NULL,
  `qop3` VARCHAR(255) DEFAULT NULL,
  `qop4` VARCHAR(255) DEFAULT NULL,
  `qcorrect_answer` INT DEFAULT NULL,
  PRIMARY KEY (`qid`)
) ;

--
-- Table structure for table `examquestiontable`
--
-- This is a mapping table to link questions to a specific exam.
--

CREATE TABLE `examquestiontable` (
  `eid` INT NOT NULL,
  `qid` INT NOT NULL,
  PRIMARY KEY (`eid`, `qid`),
  KEY `qid` (`qid`),
  CONSTRAINT `examquestiontable_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `exam` (`eid`) ON DELETE CASCADE,
  CONSTRAINT `examquestiontable_ibfk_2` FOREIGN KEY (`qid`) REFERENCES `question` (`qid`) ON DELETE CASCADE
) ;

--
-- Table structure for table `student_group`
--
-- This table maps students to a specific group.
--

CREATE TABLE `student_group` (
  `group_id` INT NOT NULL,
  `sid` INT NOT NULL,
  PRIMARY KEY (`group_id`, `sid`),
  KEY `sid` (`sid`),
  CONSTRAINT `student_group_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE CASCADE
) ;

--
-- Table structure for table `instructor_group`
--
-- This table maps instructors to a specific group.
--

CREATE TABLE `instructor_group` (
  `group_id` INT NOT NULL,
  `iid` INT NOT NULL,
  PRIMARY KEY (`group_id`, `iid`),
  KEY `iid` (`iid`),
  CONSTRAINT `instructor_group_ibfk_1` FOREIGN KEY (`iid`) REFERENCES `instructor` (`iid`) ON DELETE CASCADE
) ;

--
-- Table structure for table `examstudenttable`
--
-- This table tracks which students have taken which exams.
-- It generates a unique ID for each attempt.
--

CREATE TABLE `examstudenttable` (
  `estid` VARCHAR(255) NOT NULL, -- A unique ID for the exam attempt
  `eid` INT NOT NULL,
  `sid` INT NOT NULL,
  `datetimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`estid`),
  KEY `eid` (`eid`),
  KEY `sid` (`sid`),
  CONSTRAINT `examstudenttable_ibfk_1` FOREIGN KEY (`eid`) REFERENCES `exam` (`eid`) ON DELETE CASCADE,
  CONSTRAINT `examstudenttable_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE CASCADE
) ;

--
-- Table structure for table `result`
--
-- This table stores the results for each student's exam attempt.
--

CREATE TABLE `result` (
  `estid` VARCHAR(255) NOT NULL,
  `total_questions` INT DEFAULT NULL,
  `questions_attempted` INT DEFAULT NULL,
  `correct_answer` INT DEFAULT NULL,
  `marks_obtained` INT DEFAULT NULL,
  `total_marks` INT DEFAULT NULL,
  PRIMARY KEY (`estid`),
  CONSTRAINT `result_ibfk_1` FOREIGN KEY (`estid`) REFERENCES `examstudenttable` (`estid`) ON DELETE CASCADE
) ;

--
-- Table structure for table `studentquestionanswertable`
--
-- This table stores the specific answer a student chose for each question in an exam.
--

CREATE TABLE `studentquestionanswertable` (
  `estid` VARCHAR(255) NOT NULL,
  `qid` INT NOT NULL,
  `choosen_option` INT DEFAULT NULL,
  PRIMARY KEY (`estid`, `qid`),
  KEY `qid` (`qid`),
  CONSTRAINT `studentquestionanswertable_ibfk_1` FOREIGN KEY (`estid`) REFERENCES `examstudenttable` (`estid`) ON DELETE CASCADE,
  CONSTRAINT `studentquestionanswertable_ibfk_2` FOREIGN KEY (`qid`) REFERENCES `question` (`qid`) ON DELETE CASCADE
) ;
