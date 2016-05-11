package learn.reflect.generic;

import java.util.List;

public class A extends C{
	private Integer a;
	private B b;
	private List<D> list;
	public List<D> getList() {
		return list;
	}
	public void setList(List<D> list) {
		this.list = list;
	}
	public Integer getA() {
		return a;
	}
	public void setA(Integer a) {
		this.a = a;
	}
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	
	
}
