package alma.atarigo.ia;

import java.util.AbstractList;
import java.util.List;

public class PartialList<T> extends AbstractList<T> {
	List<T> source;
	int size;
	int index;
	
	public PartialList(List<T> src,int idx){
		this.source = src;
		this.size = src.size();
		this.index = Math.min(idx,this.size);
		if(this.index!=this.size){
			--size;
		}
	}
	
	@Override
	public T get(int index) {
		if(index<this.index){
			return source.get(index);
		}else{
			return source.get(index+1);
		}
	}

	@Override
	public int size() {
		return size;
	}
	
}
