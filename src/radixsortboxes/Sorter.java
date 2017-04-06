
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sorter {

    List<ArrayList> bucket = new ArrayList<>();

    Random rand = new Random();

    int[] numbers;
    String[] preparedNumbers;

    boolean hadToChange;
    int indexBeingChecked = 0;
    int maxIndex;

    int iterator;

    int status;

    int iForPreparedNumbers = 0;

    int iForEmptyBucket = 0;

    int iForEmptyBucketCurrent = 0;

    int index = 0;

    int current = 0;

    boolean PLACING = true;
    boolean NEXTPASS = false;

    public Sorter(int numberOfItemsToSort) {
        numbers = new int[numberOfItemsToSort];
        preparedNumbers = new String[numbers.length];

        maxIndex = numberOfItemsToSort - 1;

        for (int i = 0; i < numberOfItemsToSort; i++) {
            numbers[i] = rand.nextInt(numberOfItemsToSort) + 1;
        }

        doOnce();
    }

    private void doOnce() {
        initBucket();
        //Check for biggest number
        int biggest = 0;
        for (int i : numbers) {
            if (i > biggest) {
                biggest = i;
            }
        }

        //check for number of algorisms
        int numberOfAlgorismsOfTheBiggestNumber = getNumberOfAlgorisms(biggest);

        //Prepares the list for sorting
        //adding the zeroes to the left so everything has the
        //same amount of algorisms
        prepareNumbers(numberOfAlgorismsOfTheBiggestNumber);

        iterator = numberOfAlgorismsOfTheBiggestNumber - 1;
    }

    public void tick() {

        //Places the current number on the correct bucket
        //based on its algorism that is currently being checked
        if (PLACING) {
            if (iForPreparedNumbers < preparedNumbers.length) {
                char temp = preparedNumbers[iForPreparedNumbers].charAt(iterator);
                int algorism = Integer.parseInt(String.valueOf(temp));
                bucket.get(algorism).add(preparedNumbers[iForPreparedNumbers]);
                iForPreparedNumbers++;

                current++;
            } else {
                PLACING = false;
                iForPreparedNumbers = 0;
                current = 0;
            }
        }
        //Take the numbers out of the bucket
        //the first number to be put in the bucket 
        //is also the first to leave
        if (!PLACING) {
            for (List<String> l : bucket) {
                for (int i = 0; i < l.size(); i++) {
                    preparedNumbers[index] = l.get(i);
                    index++;
                }
                NEXTPASS = true;
            }
            /*
            if (iForEmptyBucket < bucket.size()) {
                List<String> l = bucket.get(iForEmptyBucket);
                if (iForEmptyBucketCurrent < l.size()) {
                    preparedNumbers[index] = l.get(iForEmptyBucketCurrent);
                    index++;
                    iForEmptyBucketCurrent++;
                } else {
                    iForEmptyBucket++;
                    iForEmptyBucketCurrent = 0;
                }
            } else {
                iForEmptyBucket = 0;
                NEXTPASS = true;
            }
             */
        }
        //Clears the buckets for the next pass
        if (NEXTPASS) {
            index = 0;
            bucket.clear();
            initBucket();
            iterator--;
            if (iterator >= 0) {
                NEXTPASS = false;
                PLACING = true;
            }
        }
        //with each pass the iterator decreases
        //so that every algorism in every number is checked
        int convertIndex = 0;
        for (String s : preparedNumbers) {
            numbers[convertIndex] = Integer.parseInt(s);
            convertIndex++;
        }
    }

    public void render(Graphics g) {
        int[] condensed = condenseAllBuckets();
        for (int i = 0; i < condensed.length; i++) {
            if (i == current) {
                g.setColor(Color.CYAN);
                g.fillRect(i * Game.BOX_WIDTH, Game.WINDOW_HEIGHT - condensed[i] * Game.BOX_HEIGHT - 31, Game.BOX_WIDTH, condensed[i] * Game.BOX_HEIGHT);
                g.setColor(Color.CYAN);
                g.drawRect(i * Game.BOX_WIDTH, Game.WINDOW_HEIGHT - condensed[i] * Game.BOX_HEIGHT - 31, Game.BOX_WIDTH, condensed[i] * Game.BOX_HEIGHT);
            } else {
                g.setColor(Color.RED);
                g.fillRect(i * Game.BOX_WIDTH, Game.WINDOW_HEIGHT - condensed[i] * Game.BOX_HEIGHT - 31, Game.BOX_WIDTH, condensed[i] * Game.BOX_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawRect(i * Game.BOX_WIDTH, Game.WINDOW_HEIGHT - condensed[i] * Game.BOX_HEIGHT - 31, Game.BOX_WIDTH, condensed[i] * Game.BOX_HEIGHT);
            }
        }
    }

    private int[] condenseAllBuckets() {
        int[] condensed = numbers;
        int index = 0;
        for (List<String> l : bucket) {
            for (int i = 0; i < l.size(); i++) {
                condensed[index] = Integer.parseInt(l.get(i));
                index++;
            }
        }

        return condensed;
    }

    int getNumberOfAlgorisms(int number) {
        //returns the number of algorisms
        //so that the number with most algorisms is known
        int numberOfAlgorisms = 0;
        if (number <= 9) {
            numberOfAlgorisms = 1;
        } else if (number > 9) {
            numberOfAlgorisms = 2;
        }
        if (number > 99) {
            numberOfAlgorisms = 3;
        }
        if (number > 999) {
            numberOfAlgorisms = 4;
        }

        return numberOfAlgorisms;
    }

    private void prepareNumbers(int numberOfAlgorismsOfTheBiggestNumber) {
        int index = 0;
        for (int n : numbers) {
            int numAlg = getNumberOfAlgorisms(n);
            String temp = String.valueOf(n);
            if (numberOfAlgorismsOfTheBiggestNumber - numAlg == 2) {
                String zeroes = "00";
                temp = zeroes.concat(temp);
                preparedNumbers[index] = temp;
                index++;
            } else if (numberOfAlgorismsOfTheBiggestNumber - numAlg == 1) {
                String zeroes = "0";
                temp = zeroes.concat(temp);
                preparedNumbers[index] = temp;
                index++;
            } else {
                preparedNumbers[index] = temp;
                index++;
            }
        }
    }

    private void initBucket() {
        //Initializes the bucket of buckets
        for (int i = 0; i < 10; i++) {
            bucket.add(new ArrayList<String>());
        }
    }

}
