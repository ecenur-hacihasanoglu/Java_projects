import java.util.ArrayList;
public class SkipList {
	public static void main(String[] args) {
		SkipList test = new SkipList();
		test.insertNode(40, "B");
		test.insertNode(30, "A");
		test.insertNode(50, "C");
		test.insertNode(60, "D");
		test.insertNode(70, "EEEEE");
		test.insertNode(90, "F");
		test.insertNode(100, "GGGGGGGGGGGGG");
		test.insertNode(110, "J");
		test.insertNode(120, "KKKKK");
		test.insertNode(130, "N");
		test.insertNode(140, "L");
		test.insertNode(150, "M");
		test.insertNode(20, "O");
		test.insertNode(45, "p");
		System.out.println("g position find");
		test.remove(90);
		test.skipSearch(100);
		System.out.println("Size: " + test.size());

		System.out.println(test);

	}

	public class SkipNode {
		private String val;
		private int key;
		private SkipNode left;
		private SkipNode right;
		private SkipNode up;
		private SkipNode down;

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public SkipNode getLeft() {
			return left;
		}

		public void setLeft(SkipNode left) {
			this.left = left;
		}

		public SkipNode getRight() {
			return right;
		}

		public void setRight(SkipNode right) {
			this.right = right;
		}

		public SkipNode getUp() {
			return up;
		}

		public void setUp(SkipNode up) {
			this.up = up;
		}

		public SkipNode getDown() {
			return down;
		}

		public void setDown(SkipNode down) {
			this.down = down;
		}

		public SkipNode(int key, String val) {
			this.key = key;
			this.val = val;
			this.left = null;
			this.right = null;
			this.up = null;
			this.down = null;

		}

		public SkipNode(SkipNode lowerNode) {
			this.val = lowerNode.val;
			this.down = lowerNode;
			this.left = null;
			this.right = null;
			this.up = null;
			this.key = lowerNode.key;
			lowerNode.up = this;

		}
	}

	private SkipNode head;
	private SkipNode tail;
	int height;
	private SkipNode[] heads = new SkipNode[100];
	int n = 0;

	public SkipList() {
		head = new SkipNode(Integer.MIN_VALUE, "-infinite");
		tail = new SkipNode(Integer.MAX_VALUE, "+infinite");
		head.right = tail;
		tail.left = head;
		heads[0] = head;
		height = 0;
	}

	public void addbetween(SkipNode node, SkipNode b, SkipNode a) {
		node.left = b;
		node.right = a;
		b.right = node;
		a.left = node;
	}

	public SkipNode skipSearch(int key) {
		SkipNode curr = head;
		int count = 0;
		while (curr.down != null) {
			
			curr = curr.down;
			while (curr.right.right != null && curr.right.key <= key) {
				count++;
				curr = curr.right;
			}
		}
		System.out.println(count);
		return curr;
	}

	public SkipNode insertNode(int key, String data) {
		SkipNode p = skipSearch(key);
		int level = 0;
		SkipNode newNode = null;

		while (level == 0 || flipCoin()) {

			if (newNode == null) {
				newNode = new SkipNode(key, data);
			} else {
				newNode = new SkipNode(newNode);
			}

			if (height <= level) {
				createNewLayer();
			}
			addbetween(newNode, p, p.right);
			while (p.up == null && p.key != Integer.MIN_VALUE) {
				p = p.left;
			}
			p = p.up;
			level++;
		}
		n++;

		return newNode;
	}

	public boolean remove(int key) {
		ArrayList<SkipNode> ps = new ArrayList<>();

		SkipNode curr = this.head;
		while (curr != null) {
			while (curr.right != null && curr.right.key < key) {
				curr = curr.right;
			}

			if (curr.right.key == key) {
				ps.add(curr);
			}

			curr = curr.down;
		}

		for (int i = 0; i < ps.size(); i++) {
			combineRight(ps.get(i));
		}

		return true;
	}
	private void combineRight(SkipNode node) {
		SkipNode update = node;
		SkipNode delete = update.right;

		update.right = delete.right;
		delete.right.left = update;

		delete.up = null;
		delete.down = null;
	}

	private void createNewLayer() {
		SkipNode newHead = new SkipNode(Integer.MIN_VALUE, "-infinite");
		SkipNode newTail = new SkipNode(Integer.MAX_VALUE, "+infinite");

		newHead.right = newTail;
		newTail.left = newHead;

		head.up = newHead;
		tail.up = newTail;
		newHead.down = head;
		newTail.down = tail;
		head = newHead;
		tail = newTail;
		height++;
		heads[height] = head;
	}

	private boolean flipCoin() {
		return Math.random() >= 0.5;
	}

	public String toString() {
		SkipNode point = heads[0].right;
		String print = "";
		for (int i = height; i >= 0; i--) {
			SkipNode curr = heads[i];
			point = heads[0];
			while (curr != null) {
				if (point.key == curr.key) {
					if (curr.key != Integer.MAX_VALUE && curr.key != Integer.MIN_VALUE) {
						print += "-|" + curr.val + "|-";

					} else {
						print += "|" + curr.val + "|";
					}
					curr = curr.right;
					point = point.right;
				} else {
					while (point.key != curr.key) {
						for (int j = 0; j < point.val.length() + 4; j++) {
							print += "-";
						}
						point = point.right;
					}
				}
			}
			print += "\n";
			curr = heads[i].right;
			point = heads[0].right;
			if(i!=0){
				print+="   |	     ";
			}
			while (curr != null && i != 0) {
				if (point.key == curr.key) {

					print += "|";

					for (int j = 0; j < point.val.length() + 3; j++) {
						print += " ";
					}
					curr = curr.right;
					point = point.right;
				} else {
					while (point.key != curr.key) {
						for (int j = 0; j < point.val.length() + 4; j++) {
							print += " ";
						}
						point = point.right;
					}
				}
			}
			print += "\n";

		}

		return print;
	}

	private int size() {
		return n;

	}

}