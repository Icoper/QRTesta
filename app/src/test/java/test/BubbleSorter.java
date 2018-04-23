package test;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BubbleSorter {
    String TAG = "BubbleSorter";

    public BubbleSorter() {
    }

    public void sortOne() {
        Integer[] testData = new Integer[100];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = i;
        }
        List<Integer> testDataList = Arrays.asList(testData);
        Collections.shuffle(testDataList);
        testData = (Integer[]) testDataList.toArray();
        showArray(testData);

        for (int i = 0; i < testData.length - 1; i++) {
            for (int j = i; j < testData.length - 1; j++) {
                if (testData[i] < testData[j]) {
                    int temp = testData[j];
                    testData[j] = testData[i];
                    testData[i] = temp;
                }
            }
        }

    }

    public void bubbleSort() {
        boolean isFound = true;
        Integer[] testData = new Integer[100];
        for (int i = 0; i < testData.length; i++) {
            testData[i] = i;
        }
        List<Integer> testDataList = Arrays.asList(testData);
        Collections.shuffle(testDataList);
        testData = (Integer[]) testDataList.toArray();
        showArray(testData);


        for (int i = 0; i < testData.length - 1; i++) {
            for (int j = i; j < testData.length; j++) {
                if (testData[i] < testData[j]) {
                    int temp = testData[j];
                    testData[j] = testData[i];
                    testData[i] = temp;
                }
            }
        }

        showArray(testData);

    }

    private void showArray(Integer[] integers) {
        System.out.println(Arrays.toString(integers));
    }
}