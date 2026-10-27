package org.ccrto.openapi.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import junit.framework.TestCase;

/**
 * 
 * ProcessArrayUtilsTest
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ProcessArrayUtilsTest extends TestCase {

	public static int[] generateRandomArrayLoop() {
		int[] result = new int[2000000];
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < result.length; i++) {
			result[i] = random.nextInt(-255, 256);
		}
		return result;
	}

	/**
	 * Process array method based on {@code ArrayList}
	 * 
	 * @param input array
	 * @return
	 */
	public static List<Integer> processArrayList(int[] input) {
		/* Step 1. Init result */
		List<Integer> result = new ArrayList<>();
		processList(input, result);

		return result;
	}

	/**
	 * Process array method based on {@code LinkedList}
	 * 
	 * @param input array
	 * @return
	 */
	public static List<Integer> processLinkedList(int[] input) {
		/* Step 1. Init result */
		List<Integer> result = new LinkedList<>();
		processList(input, result);

		return result;
	}

	/**
	 * Process list method based on {@code List} interface
	 * 
	 * @param input  array
	 * @param result result instance with different implementations
	 */
	private static void processList(int[] input, List<Integer> result) {
		for (int i : input) {
			/* Loop start */
			if (i < 0) {
				/* Add negative numbers to the list. */
				result.add(i);
			} else if (i > 0 && result.size() >= i) {
				/*
				 * The number is greater than 0 (!= 0) and falls within the range of the list's
				 * length.
				 */
				result.remove(i - 1);
			}
			/* I do nothing when the number is 0 or outside the range of the list size. */
			/* Loop end */
		}
	}

	/**
	 * Process array method based on {@code PrimitiveIntList}
	 * 
	 * @param input array
	 * @return
	 */
	public static List<Integer> processPrimitiveArray(int[] input) {
		/* Step 1. Init result */
		PrimitiveIntList result = new PrimitiveIntList(255);
		for (int i : input) {
			/* Loop start */
			if (i < 0) {
				/* Add negative numbers to the list. */
				result.add(i);
			} else if (i > 0 && result.size() >= i) {
				/*
				 * The number is greater than 0 (!= 0) and falls within the range of the list's
				 * length.
				 */
				result.remove(i - 1);
			}
			/* I do nothing when the number is 0 or outside the range of the list size. */
			/* Loop end */
		}
		return result.toList();
	}

	/**
	 * Testing method, performance comparison of various list implementations.
	 */
	public void testProcessArray() {
		long startTime, endTime;
		List<Integer> result;
		result = processArrayList(new int[] { -1, -2, -3, 2 });
		System.out.println(Arrays.toString(result.toArray()));
		assertEquals(Arrays.asList(-1, -3), result);

		result = processArrayList(new int[] { -1, -2, -3, 2, 2 });
		System.out.println(Arrays.toString(result.toArray()));
		assertEquals(Arrays.asList(-1), result);

		int winArray = 0;
		int winLinked = 0;
		int winPrimitive = 0;
		for (int i = 0; i < 100; i++) {
			int[] test1 = generateRandomArrayLoop();

			startTime = System.currentTimeMillis();
			processArrayList(test1);
			endTime = System.currentTimeMillis();
			long arrayTime = endTime - startTime;
			System.out.println("ArrayList process time: " + arrayTime + "[ms]");

			startTime = System.currentTimeMillis();
			processLinkedList(test1);
			endTime = System.currentTimeMillis();
			long linkedTime = endTime - startTime;
			System.out.println("LinkedList process time: " + (linkedTime) + "[ms]");

			startTime = System.currentTimeMillis();
			processPrimitiveArray(test1);
			endTime = System.currentTimeMillis();
			long primitiveTime = endTime - startTime;
			System.out.println("PrimitiveIntList process time: " + (primitiveTime) + "[ms]");

			if (linkedTime < arrayTime && linkedTime < primitiveTime) {
				winLinked++;
			} else if (arrayTime < linkedTime && arrayTime < primitiveTime) {
				winArray++;
			} else if (primitiveTime < linkedTime && primitiveTime < arrayTime) {
				winPrimitive++;
			}
		}

		System.out.println(
				"ArrayList vs LinkedList vs PrimitiveIntList: " + winArray + " : " + winLinked + " : " + winPrimitive);

	}

	/**
	 * PrimitiveIntList - Custom implementation of a list based on a primitive
	 * array.
	 *
	 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
	 * @version $Revision: 1.1 $
	 *
	 */
	public static class PrimitiveIntList {

		private int[] data;
		private int size;

		public PrimitiveIntList(int initialCapacity) {
			this.data = new int[initialCapacity];
			this.size = 0;
		}

		public void add(int element) {
			if (size == data.length) {
				/* If necessary, we increase the size of the array by 50%. */
				data = Arrays.copyOf(data, data.length + (data.length >> 1));
			}
			data[size++] = element;
		}

		public void remove(int index) {
			int numMoved = size - index - 1;
			if (numMoved > 0) {
				/* Shifting memory within a raw primitive `int[]` array. */
				System.arraycopy(data, index + 1, data, index, numMoved);
			}
			size--;
		}

		public int size() {
			return size;
		}

		/**
		 * Convert to `List<Integer>`
		 * 
		 * @return list
		 */
		public List<Integer> toList() {
			List<Integer> list = new ArrayList<>(size);
			for (int i = 0; i < size; i++) {
				list.add(data[i]);
			}
			return list;
		}

	}
}
