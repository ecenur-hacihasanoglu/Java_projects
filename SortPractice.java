
public class SortPractice {

	public static void main(String[] args) {
		int[] B = { 0, 10,9,4,2,1,6,7,8,3,5 };
//		insertion_sort(B);
//		MergeSort(B, 1, B.length-1);
//		MaxHeapify(B, 1, 10);		
//		HeapSort(B);
		quicksort(B, 1, B.length-1);
		print(B);
	}

	static void print(int[] A) {
		for (int i = 1; i < A.length; i++) {
			System.out.print(A[i] + ", ");
		}
		System.out.println();
	}

	static void swap(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	static void insertion_sort(int[] A) {
		for (int j = 2; j < A.length; j++) {
			for (int i = j; i > 1; i--) {
				if (A[i] < A[i - 1]) {
					swap(A, i, i - 1);
				} else
					break;
			}
		}

	}

	static void MergeSort(int[] A, int p, int r) {
		if (p < r) {
			int mid = (p + r) / 2;
			MergeSort(A, p, mid);
			MergeSort(A, mid + 1, r);
			Merge(A, p, mid, r);
			print(A);
		}
	}

	static void Merge(int[] A, int p, int mid, int r) {
		int n1 = mid - p + 1 + 1;
		int n2 = r - mid + 1;
		int[] L = new int[n1 + 1];
		int[] R = new int[n2 + 1];
		L[n1] = Integer.MAX_VALUE;
		R[n2] = Integer.MAX_VALUE;
		for (int i = 1; i < n1; i++) {
			L[i] = A[i + p - 1];
		}

		for (int i = 1; i < n2; i++) {
			R[i] = A[i + mid];
		}

		int left = 1;
		int right = 1;
		for (int i = p; i <= r; i++) {
			if (L[left] < R[right]) {
				A[i] = L[left];
				left++;
			} else {
				A[i] = R[right];
				right++;
			}
		}
	}

	static int left(int[] A, int i) {
		return 2 * i;
	}

	static int right(int[] A, int i) {
		return 2 * i + 1;
	}

	static void MaxHeapify(int[] A, int i, int n) {

		int left = left(A, i);
		int right = right(A, i);
		int largest = i;
		if (left <= n && A[left] > A[i]) {
			largest = left;
		}
		if (right <= n && A[right] > A[largest]) {
			largest = right;
		}
		if (largest != i) {
			swap(A, i, largest);
			MaxHeapify(A, largest, n);
		}
	}

	public static void BuildMaxHeap(int[] A,int n) {
		for (int i = n / 2; i > 0; i--) {
			MaxHeapify(A, i, n);
		}
	}
	public static void HeapSort(int[] A) {
		int n= A.length-1;
		BuildMaxHeap(A,n);
		for (int i = n; i > 1; i--) {
			swap(A, 1, i);
			n--;
			BuildMaxHeap(A,n);
		}

	}
	public static void quicksort(int[] A, int p, int r) {
		if(p<r) {
			int q= partition(A,p,r);
			quicksort(A, p, q-1);
			quicksort(A, q, r);
		}

	}
	public static int partition(int[] A,int p, int r) {
		int i=p-1;
		int pivot=A[r];
		for (int j = p; j < r; j++) {
			if(A[j]<=pivot) { 
				i++;
				swap(A, i,j);
			}
		}
		swap(A, i+1, r);
		return i+1;
	}

}
