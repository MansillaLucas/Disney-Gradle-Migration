MAVEN=target/disney-1.0.0.jar
GRADLE=build/libs/disney-1.0.0.jar

ls -l --block-size=M $MAVEN  | awk '{print $9, $5}'
ls -l --block-size=M $GRADLE  | awk '{print $9, $5}'