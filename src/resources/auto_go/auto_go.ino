//    The direction of the car's movement
//  ENA   ENB   IN1   IN2   IN3   IN4   Description
//  HIGH  HIGH  HIGH  LOW   LOW   HIGH  Car is runing forward
//  HIGH  HIGH  LOW   HIGH  HIGH  LOW   Car is runing back
//  HIGH  HIGH  LOW   HIGH  LOW   HIGH  Car is turning left
//  HIGH  HIGH  HIGH  LOW   HIGH  LOW   Car is turning right
//  HIGH  HIGH  LOW   LOW   LOW   LOW   Car is stoped
//  HIGH  HIGH  HIGH  HIGH  HIGH  HIGH  Car is stoped
//  LOW   LOW   N/A   N/A   N/A   N/A   Car is stoped


//define L298n module IO Pin
#define ENB 5
#define IN1 7
#define IN2 8
#define IN3 9
#define IN4 11
#define ENA 6

#define DCONST 14.2857142857
#define ACONST 2

void tF(int dist) {
  delay(100);
  stop();
  delay(100);
  forward();
  delay((dist*DCONST));
  stop();
}

void tTR(int theta) {
  delay(100);
  stop();
  delay(100);
  if(theta>0) {
    right();
  } else {
    left();
  }
  delay(abs(theta*ACONST));
  stop();
}

void stop() {
   digitalWrite(ENA, LOW);    //enable L298n A channel
  digitalWrite(ENB, LOW);    //enable L298n B channel
}

void forward(){
  digitalWrite(ENA, HIGH);    //enable L298n A channel
  digitalWrite(ENB, HIGH);    //enable L298n B channel
  digitalWrite(IN1, HIGH);    //set IN1 hight level
  digitalWrite(IN2, LOW);     //set IN2 low level
  digitalWrite(IN3, LOW);     //set IN3 low level
  digitalWrite(IN4, HIGH);    //set IN4 hight level
  Serial.println("Forward");  //send message to serial monitor
}

void back(){
  digitalWrite(ENA, HIGH);
  digitalWrite(ENB, HIGH);
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, HIGH);
  digitalWrite(IN3, HIGH);
  digitalWrite(IN4, LOW);
  Serial.println("Back");
}

void left(){
  digitalWrite(ENA, HIGH);
  digitalWrite(ENB, HIGH);
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, HIGH);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, HIGH);
  Serial.println("Left");
}

void right(){
  digitalWrite(ENA, HIGH);
  digitalWrite(ENB, HIGH);
  digitalWrite(IN1, HIGH);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, HIGH);
  digitalWrite(IN4, LOW);
  Serial.println("Right");
}

void loop() {}

void setup() {
  Serial.begin(9600); 
  stop();
 //WRITE//
tTR(45);
tF(68);
tTR(-45);
tF(102);
tTR(-45);
tF(65);
tTR(0);
}