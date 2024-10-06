package org.sopt.semina1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body){
        Diary diary = new Diary(null, body);

        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

    void modifyDiary(final long id, final String body){
        diaryRepository.modify(id, body);
    }

    void deleteDiary(final long id){
        diaryRepository.delete(id);
    }
}