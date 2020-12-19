package com.dsmpear.main.service.comment;

import com.dsmpear.main.entity.comment.Comment;
import com.dsmpear.main.entity.comment.CommentRepository;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.CommentNotFoundException;
import com.dsmpear.main.exceptions.PermissionDeniedException;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.request.CommentRequest;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ReportRepository reportRepository;
    private final AuthenticationFacade authenticationFacade;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void createComment(CommentRequest commentRequest) {
        User user = null;
        if(authenticationFacade.isLogin()) {
            user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }else {
            throw new PermissionDeniedException();
        }


        reportRepository.findById(commentRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        commentRepository.save(
                Comment.builder()
                    .content(commentRequest.getContent())
                    .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                    .reportId(commentRequest.getReportId())
                    .userEmail(user.getEmail())
                    .build()
        );

    }

    @Override
    public Integer updateComment(Integer commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        User user = null;
        if(authenticationFacade.isLogin()) {
            user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }else {
            throw new PermissionDeniedException();
        }


        if(!user.getEmail().equals(comment.getUserEmail())) {
            throw new PermissionDeniedException();
        }

        commentRepository.save(comment.updateContent(content));

        return commentId;
    }
    @Override
    public void deleteComment(Integer commentId) {
        User user = null;
        if(authenticationFacade.isLogin()) {
            user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                    .orElseThrow(UserNotFoundException::new);
        }else {
            throw new PermissionDeniedException();
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        if(!(comment.getUserEmail().equals(user.getEmail()))){
            throw new PermissionDeniedException();
        }

        commentRepository.delete(comment);
    }

}
