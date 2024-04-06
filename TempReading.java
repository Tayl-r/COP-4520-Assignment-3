import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TempReading extends Thread 
{
    private static Semaphore sensor = new Semaphore(8);
    private static List<Integer> temperatureReadings = Collections.synchronizedList(new ArrayList<>());

    public TempReading(String name){
        this.setName(name);
    }


    @Override
    public void run(){

        try{
            sensor.acquire();
            getTempReadings();

        } catch (InterruptedException e){

            e.printStackTrace();

        } finally {

            for (int i = 0; i < 10; i++){
                if (i < 10 / 2){
                    System.out.println(this.getName() + " " + i + ": " +temperatureReadings.get(i));
                }
                else{
                    System.out.println(this.getName() + " " + i + ": " +temperatureReadings.get(i));
                }
            }

            sensor.release();

        }
       

    }

    public static void getAllReadings(){

        for (Integer test : temperatureReadings) {
            System.out.println(test);
        }
    }

    public void getTempReadings(){
        Random random = new Random();
        
        for (int i = 0; i < 10; i++) {
            temperatureReadings.add(random.nextInt(171) - 100); // Generate numbers between -100 and 70
        }
        Collections.sort(temperatureReadings);
    }

    public static void main(String[] args) {
        TempReading[] threads = new TempReading[8];
        for (int i = 0; i < 8; i++){
            threads[i] = new TempReading("Sensor-"+i);
            threads[i].start();
        }

        for (TempReading thread : threads) {
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
        getAllReadings();
    }



}
