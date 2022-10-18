package repository;

import domain.Member;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepository implements FileBaseRepository {

    private final Map<String, Member> members;

    public Optional<Member> findById(String id) {
        return Optional.ofNullable(members.get(id));
    }

    public void save(Member member) {
        members.put(member.getUserid(), member);
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    @Override
    public void destroy() {

    }
}
