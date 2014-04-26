
package com.jd.mvc.cache.face;
/**
 * LRU 接口
 * @author liubing1@jd.com
 *
 */
public interface ICache<KEY,VALUE> {
	void put ( KEY key, VALUE value );

	VALUE get ( KEY key );

	VALUE getSilent ( KEY key );

    void remove ( KEY key );

    int size ();

}
