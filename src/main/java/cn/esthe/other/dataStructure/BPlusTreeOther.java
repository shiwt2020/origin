package cn.esthe.other.dataStructure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 5阶 B+ 树，数据唯一，已实现增加，删除，查找，按层遍历等
 * 开发时遇到的错误
 * 1、在节点复制时，复制信息不彻底，如没有将指针也进行移动复制
 * 2、指针指向链接不全，有点是单项，而很容易忽略要 双向链接 的情况
 * 3、指针改变时容易发生逻辑错误，如当指针指向发生改变时，容易使定位指针发生定位失效，不再指向原来的节点
 * @author lenovo
 */
public class BPlusTreeOther {
	
	/**
	 * MaxOrder=5
	 */
	private final int MaxOrder=5;

	private class BTreeNode{
		int nodeTotal=0;                                  // "Max" = 5 ; 记录本节点个数
		int[] values=new int[MaxOrder];                   // 用于存储数据
		BTreeNode[] children=new BTreeNode[MaxOrder];     // 子节点指针
		
		BTreeNode parent=null;
		
		BTreeNode frontDataLeaf = null;          // （仅用于叶子）直接前驱叶子数据节点
		BTreeNode laterDataLeaf = null;          // （仅用于叶子）直接后继叶子数据节点
		
		public BTreeNode(int data) {             // 新建节点时默认插入第一个中
			this.values[0]=data;
			this.nodeTotal++;
		}
		
	}
	
	private BTreeNode ROOT=null;
	private int SIZE=0;
	public int size() {
		return this.SIZE;
	}
	
	public void visitAllLeavesNode() {
		if(this.ROOT==null) {
			System.out.println("The tree is null by 'visitAllLeavesNode'!");
			return;
		}
		
		int sum=0;
		
		BTreeNode tempNode = this.ROOT;
		while (tempNode.children[0]!=null) {						// visiting from front to back
			tempNode=tempNode.children[0];
		}
		while (tempNode!=null) {
			System.out.print("(");
			for(int i=0; i<tempNode.nodeTotal; i++) {
				System.out.print(tempNode.values[i]+",");
				sum++;
			}
			System.out.print(")");
			tempNode=tempNode.laterDataLeaf;
		}
		System.out.println();
		
		tempNode = this.ROOT;										// visiting from back to front
		while (tempNode.children[tempNode.nodeTotal-1]!=null) {
			tempNode=tempNode.children[tempNode.nodeTotal-1];
		}
		while (tempNode!=null) {
			System.out.print("(");
			for(int i=tempNode.nodeTotal-1; i>-1; i--) {
				System.out.print(tempNode.values[i]+",");
			}
			System.out.print(")");
			tempNode=tempNode.frontDataLeaf;
		}
		System.out.println();
		
		System.out.println("(visitAllLeavesNode): The data of leaves is "+sum);
		
	}
	
	public void visitAllNode() {
		
		if(this.ROOT==null) {
			System.out.println("The tree is null by 'visitAllNode'!");
			return;
		}
		
		Queue<BTreeNode> aQueue=new LinkedList<BTreeNode>();
		int thisFlower=0;
		int nextFlower=0;
		
		aQueue.add(this.ROOT);
		thisFlower++;
		BTreeNode temp;
		while (!aQueue.isEmpty()) {
			temp = aQueue.poll();
			thisFlower--;
			
			System.out.print("(");
			for(int i=0; i<temp.nodeTotal; i++) {
				if(temp.children[i]!=null) {
					aQueue.add(temp.children[i]);
					nextFlower++;
				}
				if(i<temp.nodeTotal-1) {
					System.out.print(temp.values[i]+",");
				}else {
					System.out.print(temp.values[i]);
				}
			}
			System.out.print(")");
			
			if(thisFlower==0) {
				thisFlower=nextFlower;
				nextFlower=0;
				System.out.println();
			}
			
		}
		
	}
	
	public boolean findData(int data) {
		if(this.ROOT==null) return false;
		if(findTheNodePositionForRemove(data,this.ROOT)!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * @param data : only 'int'
	 */
	public void add(int data) {
		if(this.ROOT==null) {                     // 如树为空，创建根节点
			this.ROOT = new BTreeNode(data);
			this.SIZE = 1;
			return;
		}
		
		BTreeNode goalNode = findTheNodePositionForAdd(data, this.ROOT);     // 找到要插入的叶子节点位置
		
		if(goalNode == null) return;              // 如果节点为空，即数据已存在，直接返回
		
		insertTheAddDataToTheNode(data, goalNode);
		this.SIZE++;				              // 树的数据总数加一

	}
	
	private void insertTheAddDataToTheNode(int data, BTreeNode goalNode) {
		
		if(goalNode.nodeTotal<this.MaxOrder) {   // 如果该当前节点数据小于总容量
			dataInsertInSequence(goalNode,  data);
			if(data==goalNode.values[goalNode.nodeTotal]) {    
				// 即插入的是该节点的最后一个节点（如插入的数据比树中最大值还大）要向上更新该节点的最大值，否则不做处理
				updataParetFormationForAdd(goalNode.parent, goalNode.values[goalNode.nodeTotal-1] ,data);
			}
			goalNode.nodeTotal++;          // 当前节点总数加一              
			return; 					   // 插入完成
		}
		
		if(goalNode.nodeTotal==this.MaxOrder) {   // 如果当前节点数据等于总容量（即不可再添加），则进行分裂

			// 节点分裂
			BTreeNode newDivisiveNode = new BTreeNode(goalNode.values[this.MaxOrder/2]);   // 先创建一个分裂节点
			goalNode.values[this.MaxOrder/2]=0;
			
			for(int i=this.MaxOrder/2+1, j=1; i<this.MaxOrder; i++) {            // 将旧节点部分数据复制到新节点
				newDivisiveNode.values[j++]=goalNode.values[i];
				goalNode.values[i]=0;
			}
			goalNode.nodeTotal=this.MaxOrder/2;									   // 计算分裂后节点存储数据个数
			newDivisiveNode.nodeTotal=this.MaxOrder-this.MaxOrder/2;
			
			if(data<newDivisiveNode.values[0]) {                                   // 判断将新插入的数据，插入前半部分，还是后半部分
				dataInsertInSequence(goalNode,data); 
				goalNode.nodeTotal++;                           				   // 节点存储数据个数加一
			}else {
				dataInsertInSequence(newDivisiveNode,data);     
				newDivisiveNode.nodeTotal++;                                       
			}
			
			//如果是叶子的原始分裂，则要对新叶子节点进行双向链表化
			if(goalNode.children[0]==null) {  
				BTreeNode tempNode=goalNode.laterDataLeaf;
				
				goalNode.laterDataLeaf=newDivisiveNode;
				newDivisiveNode.frontDataLeaf=goalNode;
				if(tempNode!=null) {
					tempNode.frontDataLeaf=newDivisiveNode;
					newDivisiveNode.laterDataLeaf=tempNode;
				}
			}
			
			nodeInsertInTree(goalNode, newDivisiveNode);
			
		}
		
	}
	
	/**
	 * 
	 * @param letfNode : has existed in the tree.
	 * @param rightNode : is being inserted to the tree.
	 */
	private void nodeInsertInTree(BTreeNode letfNode, BTreeNode rightNode) {
		// 更新节点分裂后父节点的信息
		if(letfNode.parent==null) {                 //如果没有父节点就创一个父节点
			BTreeNode newParent=new BTreeNode(letfNode.values[letfNode.nodeTotal-1]);
			newParent.children[0]=letfNode;
			letfNode.parent=newParent;
			
			newParent.values[1]=rightNode.values[rightNode.nodeTotal-1];
			newParent.nodeTotal++;
			newParent.children[1]=rightNode;
			rightNode.parent=newParent;
			
			this.ROOT=newParent;
			
			return;
		}
		
		if(letfNode.parent.nodeTotal<this.MaxOrder) {           // 如果父级容量够
			
			//插入数据
			int iLR=0;
			for(iLR=0; iLR < letfNode.parent.nodeTotal; iLR++) { if(letfNode.parent.children[iLR]==letfNode) break; }
			
			boolean theAddDataIsMax=false;         // 判断新插入的数据是不是在现有树中的最大值，如果是则要递归更新上层的最大值
			if(letfNode.parent.values[iLR]!=rightNode.values[rightNode.nodeTotal-1])theAddDataIsMax=true;
			
			letfNode.parent.values[iLR]=letfNode.values[letfNode.nodeTotal-1];  // 根据分裂的左节点，更新左父节点最大值
			
			for(int i = this.MaxOrder-1; i>iLR; i--) {                      //将节点中的数据后移一位，以插入新节点
				letfNode.parent.values[i]=letfNode.parent.values[i-1];
				letfNode.parent.children[i]=letfNode.parent.children[i-1];
			}
			
			letfNode.parent.values[iLR+1]=rightNode.values[rightNode.nodeTotal-1]; 
			letfNode.parent.children[iLR+1]=rightNode;                      // 父节点指向子节点
			rightNode.parent=letfNode.parent;								// 子节点指向父节点
			
			rightNode.parent.nodeTotal++;                                   // 父级节点数据加一
			
			if(theAddDataIsMax) {    
				// 即插入的是该节点的最后一个节点（如插入的数据比树中最大值还大）要向上更新该节点的最大值，否则不做处理
				if(rightNode.parent.parent!=null)
				updataParetFormationForAdd(rightNode.parent.parent, rightNode.values[rightNode.nodeTotal-2] ,rightNode.values[rightNode.nodeTotal-1]);
			}
			
			return;   // 分裂后父级容量足够的情况处理完成
		}
		
		// 如果父级容量不够，父级进行分类再向上递归
		if (letfNode.parent.nodeTotal==this.MaxOrder) {
	
			// 这里如果左右子树都在父节点的分裂节点，那么递归时将会丢失父节点的左节点（即原来的节点）
			BTreeNode parentNodeLeft = letfNode.parent;
			
			// 先修改父节点的旧值，让其记录letfNode的最大值，而不是rightNode的最大值
			int iLR=0;
			for(iLR=0; iLR < letfNode.parent.nodeTotal; iLR++) { if(letfNode.parent.children[iLR]==letfNode) break; }
			letfNode.parent.values[iLR]=letfNode.values[letfNode.nodeTotal-1];
			
			// 再将   父节点  进行分裂，
			BTreeNode newDivisiveNode = new BTreeNode(parentNodeLeft.values[this.MaxOrder/2]);   // 先创建一个分裂节点
			newDivisiveNode.children[0]=parentNodeLeft.children[this.MaxOrder/2];         // 父节点指向子节点
			newDivisiveNode.children[0].parent=newDivisiveNode;							  // 子节点指向父节点
			
			parentNodeLeft.values[this.MaxOrder/2]=0;
			parentNodeLeft.children[this.MaxOrder/2]=null;
			
			for(int i=this.MaxOrder/2+1, j=1; i<this.MaxOrder; i++) {                   // 将旧父节点部分数据转移到新节点
				newDivisiveNode.values[j]=parentNodeLeft.values[i]; 					  
				newDivisiveNode.children[j]=parentNodeLeft.children[i];				  
				newDivisiveNode.children[j++].parent=newDivisiveNode;
				
				parentNodeLeft.values[i]=0;
				parentNodeLeft.children[i]=null;
			}
			parentNodeLeft.nodeTotal=this.MaxOrder/2;									  // 计算分裂后节点存储数据个数
			newDivisiveNode.nodeTotal=this.MaxOrder-this.MaxOrder/2;
			
			if(rightNode.values[rightNode.nodeTotal-1]<newDivisiveNode.values[0]) {       // 判断将新插入的数据，插入前半部分，还是后半部分
				int pos = dataInsertInSequence(parentNodeLeft, rightNode.values[rightNode.nodeTotal-1]); 
				parentNodeLeft.children[pos]=rightNode;                                   // 父节点指向子节点
				rightNode.parent = parentNodeLeft;										  // 子节点指向父节点
				parentNodeLeft.nodeTotal++;                             				  // 计算分裂后节点存储数据个数
			}else {
				int pos = dataInsertInSequence(newDivisiveNode, rightNode.values[rightNode.nodeTotal-1]); 
				newDivisiveNode.children[pos]=rightNode;
				rightNode.parent = newDivisiveNode;
				newDivisiveNode.nodeTotal++;
			}
			// 分裂结束

			nodeInsertInTree(parentNodeLeft, newDivisiveNode);  						 // 向上递归插入
		}
		
		// 至此，插入且要分裂的情况处理完成
	}
	
	private int dataInsertInSequence(BTreeNode goalNode, int data) {
		int i=0;
		for(i=0; i<goalNode.nodeTotal; i++) {   
			if(data<goalNode.values[i]){
				break;
			}
		}
		int tempValue;
		BTreeNode tempNode,dataNode=null;
		for(int j=i; j<goalNode.nodeTotal+1; j++) {    // 将数据插入当前节点
			tempValue=goalNode.values[j];
			tempNode=goalNode.children[j];
			
			goalNode.values[j]=data;
			goalNode.children[j]=dataNode;
			
			data=tempValue;
			dataNode=tempNode;
		}
		return i;
	}
	
	/**
	 * updating formation of parent node about a child maximal value.
	 * @param nodeIndex
	 * @param oldData
	 * @param newData
	 */
	private void updataParetFormationForAdd(BTreeNode nodeIndex, int oldData, int newData) {
		if(nodeIndex==null) {
			return;
		}else {
			int i=0;
			for(i=0; i<nodeIndex.nodeTotal; i++) {
				if(nodeIndex.values[i] == oldData) break;
			}
			if(i==nodeIndex.nodeTotal) {   // 父节点不包含该信息，不再向上递归处理
				return;
			}else {
				nodeIndex.values[i]=newData;
				updataParetFormationForAdd(nodeIndex.parent,oldData,newData);   // 这一层有继续往上递归更新
			}
			  
		}
	}
	
	private BTreeNode findTheNodePositionForAdd(int data, BTreeNode nodeIndex){
		int i=0;
		for(i=0; i<nodeIndex.nodeTotal; i++) {   // 找到插入数据在某节点的哪两个数据之间，以确定子节点指针
			if(data==nodeIndex.values[i]) {
				return null;
			}else if(data<nodeIndex.values[i]){
				break;
			}
		}
		
		if(i==nodeIndex.nodeTotal) {             // 当插入数据比树中的最大值还要大时，指针直接移到子树的最右，不递归
			BTreeNode tempNode=this.ROOT;
			while (tempNode.children[tempNode.nodeTotal-1]!=null) {
				tempNode=tempNode.children[tempNode.nodeTotal-1];
			}
			return tempNode;
		}
		
		if(nodeIndex.children[i]==null) {        // 如果该节点为寻找的叶子节点，结束递归
			return nodeIndex;
		}else {
			return findTheNodePositionForAdd(data, nodeIndex.children[i]);
		}
		
	}
	
	public void remove(int data) {
		if(this.ROOT==null)return;
		
		BTreeNode targetNode=findTheNodePositionForRemove(data, this.ROOT);
		
		if(targetNode==null) { return;  }       
			
		int iIndex=0;
		while (iIndex<targetNode.nodeTotal) {
			if(data==targetNode.values[iIndex])break;
			iIndex++;
		}
		
		for(int i=iIndex; i<targetNode.nodeTotal-1; i++) {   // 当前节点左移一位，以删除目标数据
			targetNode.values[i]=targetNode.values[i+1];
		}
		targetNode.nodeTotal--;								 // 当前节点数据个数减一

		if(iIndex==targetNode.nodeTotal && targetNode.parent!=null) {					 // 如果删除的是当前节点的最大值，则要向上更新节点最大值
			updataParetFormationForAdd( targetNode.parent,  targetNode.values[targetNode.nodeTotal],  targetNode.values[targetNode.nodeTotal-1]);
		}
		
		// 如果此时当前节点数据个数小于最低要求，则递归处理，否则不做处理
		if(targetNode.nodeTotal < this.MaxOrder/2) removeUpwordParent(targetNode);   
		
		this.SIZE--;
		
	}
	
	private void removeUpwordParent(BTreeNode nodeIndex) {
		
		// 此时该节点为根节点，判断是否要删除该根节点，否则不做处理
		if(nodeIndex.parent==null) {                                         
			if(nodeIndex.nodeTotal==1 && nodeIndex.children[0]!=null) {      // 如果跟节点个数为1，且还有子节点，删除该根节点
				this.ROOT=nodeIndex.children[0];
				this.ROOT.parent=null;
			}
			if(nodeIndex.nodeTotal==0) this.ROOT = null;
			return;
		}

		// 如果这个节点不是跟节点，且总数小于最小要求，进行相应处理
		
		int iParentIndex=0;      // 定位当前节点在父节点中的位置
		while (iParentIndex < nodeIndex.parent.nodeTotal) {
			if(nodeIndex == nodeIndex.parent.children[iParentIndex]) break;
			iParentIndex++;
		} 
		
		// 如果有右兄弟 且 右兄弟大于最小要求数量，向右兄弟借节点
		if(iParentIndex+1 < nodeIndex.parent.nodeTotal && nodeIndex.parent.children[iParentIndex+1].nodeTotal > this.MaxOrder/2) {
			// 向右兄弟借一位数据
			nodeIndex.values[nodeIndex.nodeTotal]=nodeIndex.parent.children[iParentIndex+1].values[0];
			nodeIndex.children[nodeIndex.nodeTotal]=nodeIndex.parent.children[iParentIndex+1].children[0];
			if(nodeIndex.children[nodeIndex.nodeTotal]!=null) nodeIndex.children[nodeIndex.nodeTotal].parent=nodeIndex;
			nodeIndex.nodeTotal++;
			updataParetFormationForAdd(nodeIndex.parent, nodeIndex.values[nodeIndex.nodeTotal-2],nodeIndex.values[nodeIndex.nodeTotal-1]);
			
			for(int i=0; i < nodeIndex.parent.children[iParentIndex+1].nodeTotal-1; i++) {          // 右兄弟左移一位
				nodeIndex.parent.children[iParentIndex+1].values[i] = nodeIndex.parent.children[iParentIndex+1].values[i+1];
				nodeIndex.parent.children[iParentIndex+1].children[i] = nodeIndex.parent.children[iParentIndex+1].children[i+1];
			}
			nodeIndex.parent.children[iParentIndex+1].nodeTotal--;
			return;
		}
		
		// 如果有左兄弟且左兄弟大于最小要求数量，向左兄弟借节点
		if(iParentIndex-1 > -1 && nodeIndex.parent.children[iParentIndex-1].nodeTotal > this.MaxOrder/2) {
			for(int i=nodeIndex.nodeTotal; i>0; i--) {									   			// 当前节点右移一位
				nodeIndex.values[i]=nodeIndex.values[i-1];
				nodeIndex.children[i]=nodeIndex.children[i-1];
			}
			// 向左兄弟借一位
			nodeIndex.values[0]=nodeIndex.parent.children[iParentIndex-1].values[nodeIndex.parent.children[iParentIndex-1].nodeTotal-1];
			nodeIndex.children[0]=nodeIndex.parent.children[iParentIndex-1].children[nodeIndex.parent.children[iParentIndex-1].nodeTotal-1];
			if(nodeIndex.children[0]!=null) nodeIndex.children[0].parent=nodeIndex;
			nodeIndex.nodeTotal++;
			
			nodeIndex.parent.children[iParentIndex-1].nodeTotal--; 									// 左兄弟节点减一（减去最后一位）
			updataParetFormationForAdd(nodeIndex.parent, 
					nodeIndex.parent.children[iParentIndex-1].values[nodeIndex.parent.children[iParentIndex-1].nodeTotal], 
					nodeIndex.parent.children[iParentIndex-1].values[nodeIndex.parent.children[iParentIndex-1].nodeTotal-1]);
			return;
		}
		
		// 如果当前节点为叶子节点，且即将删除，则将前后叶子进行双向链表化
		if( nodeIndex.children[0]==null ) {                      
			if(nodeIndex.frontDataLeaf!=null) nodeIndex.frontDataLeaf.laterDataLeaf=nodeIndex.laterDataLeaf;
			if(nodeIndex.laterDataLeaf!=null) nodeIndex.laterDataLeaf.frontDataLeaf=nodeIndex.frontDataLeaf;
		}
		
		// 如果有右兄弟 但 右兄弟小于等于最小要求数量，向右兄弟合并
		if(iParentIndex+1 < nodeIndex.parent.nodeTotal && nodeIndex.parent.children[iParentIndex+1].nodeTotal<=this.MaxOrder/2) {
			// 将右兄弟右移以空出当前节点数据的位置
			for(int i = nodeIndex.parent.children[iParentIndex+1].nodeTotal + nodeIndex.nodeTotal-1; i > nodeIndex.nodeTotal-1; i--) {
				if(nodeIndex.parent.children[iParentIndex+1].nodeTotal + nodeIndex.nodeTotal-1 == 5) {
					System.out.println(nodeIndex.values[0]+","+nodeIndex.values[1]+","+nodeIndex.values[2]+","+nodeIndex.values[3]);
					System.out.println("I am '5'."+nodeIndex.parent.children[iParentIndex+1].nodeTotal + "+" + nodeIndex.nodeTotal);
				}
				nodeIndex.parent.children[iParentIndex+1].values[i]=nodeIndex.parent.children[iParentIndex+1].values[i-nodeIndex.nodeTotal];
				nodeIndex.parent.children[iParentIndex+1].children[i]=nodeIndex.parent.children[iParentIndex+1].children[i-nodeIndex.nodeTotal];
			}
			
			for(int i = nodeIndex.nodeTotal-1; i > -1; i--) {									   // 当前节点移到右兄弟节点
				nodeIndex.parent.children[iParentIndex+1].values[i]=nodeIndex.values[i];
				nodeIndex.parent.children[iParentIndex+1].children[i]=nodeIndex.children[i];
				if(nodeIndex.parent.children[iParentIndex+1].children[i]!=null)
					nodeIndex.parent.children[iParentIndex+1].children[i].parent=nodeIndex.parent.children[iParentIndex+1];
			}
			nodeIndex.parent.children[iParentIndex+1].nodeTotal += nodeIndex.nodeTotal;
			
			for(int i=iParentIndex; i<nodeIndex.parent.nodeTotal-1; i++) {                         // 父节点左移以删除数据
				nodeIndex.parent.values[i]=nodeIndex.parent.values[i+1];
				nodeIndex.parent.children[i]=nodeIndex.parent.children[i+1];
			}
			nodeIndex.parent.nodeTotal--;
		
			if(nodeIndex.parent.nodeTotal<this.MaxOrder/2) {             							   // 如果父节点也小于最小要求，则向上递归
				removeUpwordParent(nodeIndex.parent);
			}else {
				return;
			}
		}
		
		// 如果有左兄弟 但 左兄弟小于等于最小要求数量，向左兄弟合并
		if(iParentIndex-1 > -1 && nodeIndex.parent.children[iParentIndex-1].nodeTotal <= this.MaxOrder/2) {
			// 自己移到左兄弟节点，进行合并
			for(int i=0; i<nodeIndex.nodeTotal; i++) { 		
				if(nodeIndex.parent.children[iParentIndex-1].nodeTotal + i==5)System.out.println("I am '5'"+nodeIndex.nodeTotal);
				nodeIndex.parent.children[iParentIndex-1].values[nodeIndex.parent.children[iParentIndex-1].nodeTotal + i ]=nodeIndex.values[i];
				nodeIndex.parent.children[iParentIndex-1].children[nodeIndex.parent.children[iParentIndex-1].nodeTotal + i ]=nodeIndex.children[i];
				if(nodeIndex.parent.children[iParentIndex-1].children[nodeIndex.parent.children[iParentIndex-1].nodeTotal + i ] != null)
					nodeIndex.parent.children[iParentIndex-1].children[nodeIndex.parent.children[iParentIndex-1].nodeTotal + i ].parent=
							nodeIndex.parent.children[iParentIndex-1];
			}
			nodeIndex.parent.children[iParentIndex-1].nodeTotal += nodeIndex.nodeTotal;
			updataParetFormationForAdd(nodeIndex.parent, 
					nodeIndex.parent.children[iParentIndex-1].values[nodeIndex.parent.children[iParentIndex-1].nodeTotal-nodeIndex.nodeTotal-1], 
					nodeIndex.parent.children[iParentIndex-1].values[nodeIndex.parent.children[iParentIndex-1].nodeTotal-1]);
			
			for(int i=iParentIndex; i<nodeIndex.parent.nodeTotal-1; i++) {							// 父节点左移以删除数据
				nodeIndex.parent.values[i]=nodeIndex.parent.values[i+1];
				nodeIndex.parent.children[i]=nodeIndex.parent.children[i+1];
			}
			nodeIndex.parent.nodeTotal--;															// 父节点数据减一
			
			if(iParentIndex==nodeIndex.parent.nodeTotal)                // 因为此时父节点中不一定有右节点，所以要进行向上最大值更新判断
				updataParetFormationForAdd(nodeIndex.parent.parent, 
						nodeIndex.parent.values[nodeIndex.parent.nodeTotal], nodeIndex.parent.values[nodeIndex.parent.nodeTotal-1]);
			
			if(nodeIndex.parent.nodeTotal<this.MaxOrder/2) {              // 如果父节点也小于最小要求，则向上递归
				removeUpwordParent(nodeIndex.parent);
			}else {
				return;
			}
		}
		
		
	}
	
	private BTreeNode findTheNodePositionForRemove(int data, BTreeNode nodeIndex){
		int i=0;
		for(i=0; i<nodeIndex.nodeTotal; i++) if(data <= nodeIndex.values[i]) break;

		if(i==nodeIndex.nodeTotal)return null;   // 数值不存在，不递归
		
		if(nodeIndex.children[i]==null) {        // 该节点已是叶子节点
			if(nodeIndex.values[i]!=data) {
				return null;					 // 数值不存在
			} else {
				return nodeIndex;
			}
		}else {
			return findTheNodePositionForRemove(data, nodeIndex.children[i]);   // 该节点不是叶子节点，进行递归处理
		}
	}
	
	
}






