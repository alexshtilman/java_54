1. Write all three layers (Controller, Service, Data) for the functionality allowing for all students and all subjects setting average mark for a subject instead of several subject marks of a student. For example, if ‘Moshe’ has two marks of Java (70, 80) and three marks of React (60, 80,100), the result will be one mark of Java – 75 and one mark of React – 80.
   1. Controller: PUT Request (“/subjects/marks/averaging”)
   1. Service: void averagingSubjectMarks()
   1. Data: think of which queries may be applied for that averaging
1. Write the cross layers Unit tests (Controller, Service, Data) for all methods of the last home works 

# Important

in application.properties:
disable `spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults= false`

https://stackoverflow.com/questions/50480622/jpa-hibernate-select-currval-on-non-existing-sequence-relation

## How to test
1. http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/marks-controller/getDistribution
1. GET http://localhost:8080/init?n_students=35&n_subjects=20&n_marks=200&uique_marks=false&min_mark=60&max_mark=100
1. GET http://localhost:8080/marks/distribution?interval=100 - will be excat 200 marks
1. PUT http://localhost:8080/subjects - will clean all common marks
1. GET http://localhost:8080/marks/distribution?interval=100 - will be less than 200 marks ( init generate random number of marks


