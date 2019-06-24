package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers)  {

       try {
           if (inputNumbers.size() == 0)
               throw new CannotBuildPyramidException();

           if (inputNumbers == null)
               throw new CannotBuildPyramidException();
           if (inputNumbers.size() == Integer.MAX_VALUE-1)
               throw new CannotBuildPyramidException();
           bubble(inputNumbers);
           List<List<Integer>> pyramid = preparePyramid(inputNumbers);
           createPyramid(pyramid);
           Integer[][] toNextConvert = convertToIntegerArray(pyramid);
           int[][] finalPyramid = convertToIntArray(toNextConvert);
           return finalPyramid;
       }
       catch (Exception e)
       {
           throw new CannotBuildPyramidException();
       }
    }

    private void bubble(List<Integer> list)
    {
        int n = list.size();
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (list.get(j - 1) > list.get(j)) {
                    temp = list.get(j - 1);
                    list.set(j - 1, list.get(j));
                    list.set(j, temp);
                }

            }
        }
    }

    private List<List<Integer>> preparePyramid (List<Integer> list) throws Exception
    {
        List<List<Integer>> pyramid = new ArrayList<List<Integer>>();
        int stop = 1;
        for (int i =0;i<list.size();)
        {
            List<Integer> anotherRow = new ArrayList<Integer>();
            try
            {
                while (anotherRow.size()<stop)
                {
                    anotherRow.add(list.get(i));
                    i++;
                }
            }
            catch (Exception e)
            {
                throw new CannotBuildPyramidException();
            }
            pyramid.add(anotherRow);
            stop++;
        }
        return pyramid;
    }

    private Integer[][] convertToIntegerArray(List<List<Integer>> pyramid)
    {
        Integer[][] finalArray = new Integer[pyramid.size()][];
        Integer[] blankArray = new Integer[0];
        for (int i =0; i<pyramid.size();i++)
        {
            finalArray[i] = pyramid.get(i).toArray(blankArray);
        }
        return finalArray;
    }

    private int[][] convertToIntArray(Integer[][] integers)
    {
        int[][] finalPyramid = new int[integers.length][integers[0].length];
        for (int i=0;i<integers.length;i++)
        {
            for (int j=0;j<integers[i].length;j++)
            {
                finalPyramid[i][j] = integers[i][j];
            }
        }
        return finalPyramid;
    }

    private void createPyramid(List<List<Integer>> pyramid)
    {
        List<Integer>finalRow = new ArrayList<Integer>();
        List<Integer>allElements = new ArrayList<Integer>();
        for (List<Integer> l :pyramid)
        {
            for (Integer i : l)
            {
                allElements.add(i);
            }
        }
        for (Integer i: pyramid.get(pyramid.size()-1))
        {
            finalRow.add(i);
            finalRow.add(0);
        }
        finalRow.remove(finalRow.size()-1);
        int finalSize = finalRow.size();
        List<List<Integer>> tableOfZeros = new ArrayList<List<Integer>>();
        for (int i = 0; i<pyramid.size()-1;i++)
        {
            List<Integer> anotherZeroRow = new ArrayList<Integer>();
            for (int j = 0; j<finalSize;j++)
            {
                anotherZeroRow.add(0);
            }
            tableOfZeros.add(anotherZeroRow);
        }
        tableOfZeros.add(finalRow);
        int head = finalSize/2;
        boolean flag = true;
        int pyramidElementCounter = 0;
        for (int i = 0;i<tableOfZeros.size();i++)
        {
            if(i%2==0) flag=true;
            List<Integer> replacedRow = tableOfZeros.get(i);
            List<Integer> indexes = new ArrayList<>();
            for (int stepCounter = 0; stepCounter<i+1; stepCounter++)
            {
                if(flag && stepCounter==0)
                {
                  indexes.add(head);
                  flag = false;
                }
                else {
                    if(!flag && stepCounter==0)
                    {
                        flag=true;
                    }
                }
                if (stepCounter!=0 && flag)
                {
                    indexes.add(head+stepCounter);
                    indexes.add(head-stepCounter);
                    flag = false;
                }
                else {
                    if(!flag && stepCounter!=0)
                    {
                        flag=true;
                    }
                }
            }
            bubble(indexes);
            for (Integer ind:indexes)
            {
                replacedRow.set(ind,allElements.get(pyramidElementCounter));
                pyramidElementCounter++;
            }
            pyramid.set(i,replacedRow);
        }
    }
}
