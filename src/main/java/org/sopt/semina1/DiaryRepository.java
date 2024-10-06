package org.sopt.semina1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary){
        final long id = numbering.addAndGet(1);

        // 저장 과정
        storage.put(id, diary.getBody());
    }

    List<Diary> findAll(){
        // (1) diary 담는 자료구조
        final List<Diary> diaryList= new ArrayList<>();

        // (2) 저장한 값을 불러옴
        for(long index = 1; index <= numbering.intValue(); index++){
            final String body = storage.get(index);

            // (2-1) 불러온 값을 구성한 자료구조로 이관
            diaryList.add(new Diary(index, body));
        }

        // (3) 불러온 자료구조를 응답
        return diaryList;
    }

    boolean existsById(final long id){
        return storage.containsKey(id);
    }

    void modify(final long id, final String body){
        if (existsById(id)) {
            storage.put(id, body);
        } else {
            throw new IllegalArgumentException("해당 ID의 다이어리가 없어용가리~");
        }
    }

    void delete(final long id){
        if(existsById(id)){
            storage.remove(id);
        } else {
            throw new IllegalArgumentException("해당 ID의 다이어리가 없어용가리~");
        }
    }
}