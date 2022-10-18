package repository;

import domain.Member;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepository implements FileBaseDatabase {

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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./data/members")))) {
            for (Member member : members.values()) {
                writer.write(member.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
