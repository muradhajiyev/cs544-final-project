package edu.miu.cs544.medappointment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DemoTest {


    @Test
    public void testAddition(){
        Assertions.assertEquals(5, 2+3);
    }


    @Test
    public void testAddition2(){
        Assertions.assertEquals(5, 3+3);
    }
}
