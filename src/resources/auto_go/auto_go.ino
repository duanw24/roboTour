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
#define ENA 6

#define IN1 7
#define IN2 8
#define IN3 9
#define IN4 11


#define PWMCONSTa 102
#define PWMCONSTd 51

#define bVal_a 13
#define mVal_a 7.87401574803
//

#define bVal_d 3
#define mVal_d 144.33
//



//initial burst
void tF(int dist) {
  delay(200);
  forward();
  delay(50);
  stop();

  analogWrite(ENA, PWMCONSTd);    //enable L298n A channel
  analogWrite(ENB, PWMCONSTd);    //enable L298n B channel
  digitalWrite(IN1, HIGH);    //set IN1 hight level
  digitalWrite(IN2, LOW);     //set IN2 low level
  digitalWrite(IN3, LOW);     //set IN3 low level
  digitalWrite(IN4, HIGH);
  delay(mVal_d*(dist+bVal_d));
  Serial.print(mVal_d*(dist+bVal_d));
  stop();
}

void tTR(int theta) {
  delay(200);

  if(theta>0) {
    right();
    delay(50);
    stop();

    analogWrite(ENA, PWMCONSTa);
    analogWrite(ENB, PWMCONSTa);
    digitalWrite(IN1, HIGH);
    digitalWrite(IN2, LOW);
    digitalWrite(IN3, HIGH);
    digitalWrite(IN4, LOW);
  } else {
    left();
    delay(50);
    stop();

    analogWrite(ENA, PWMCONSTa);
    analogWrite(ENB, PWMCONSTa);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, HIGH);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, HIGH);

  }
  delay(abs(mVal_a*(theta-bVal_a)));
  stop();
}

void stop() {
  digitalWrite(ENA, LOW);    
  digitalWrite(ENB, LOW); 
  digitalWrite(IN1, LOW);   
  digitalWrite(IN2, LOW);    
  digitalWrite(IN3, LOW);    
  digitalWrite(IN4, LOW);
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

void loop() {
 //WRITE//
}

void setup() {
  Serial.begin(9600);
  forward();
}