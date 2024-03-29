package com.nhnacademy.marketgg.client.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.marketgg.client.dto.label.LabelRegisterRequest;
import com.nhnacademy.marketgg.client.dto.label.LabelRetrieveResponse;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthenticException;
import com.nhnacademy.marketgg.client.exception.auth.UnAuthorizationException;
import com.nhnacademy.marketgg.client.service.label.LabelService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 라벨 관리에 관련된 Controller 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin/labels")
@RequiredArgsConstructor
public class AdminLabelController {

    private final LabelService labelService;
    private static final String LABEL_DEFAULT = "redirect:/admin/labels";

    /**
     * 입력받은 정보로 라벨을 등록하는 메소드를 실행하고 다시 라벨의 Index 페이지로 이동하는 메소드입니다.
     *
     * @param labelRequest - 등록할 라벨의 입력정보를 담은 객체입니다.
     * @return 라벨을 등록하는 메소드를 실행하고 다시 라벨의 Index 페이지로 REDIRECT 합니다.
     * @throws JsonProcessingException  Json 컨텐츠를 처리할 때 발생하는 모든 문제에 대한 예외처리입니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @PostMapping("/create")
    public ModelAndView createLabel(@Valid @ModelAttribute final LabelRegisterRequest labelRequest,
                                    BindingResult bindingResult)
            throws JsonProcessingException, UnAuthenticException, UnAuthorizationException {

        if (!bindingResult.hasErrors()) {
            labelService.createLabel(labelRequest);
        }

        return new ModelAndView(LABEL_DEFAULT);
    }

    /**
     * 전체 라벨 목록을 담은 후, 라벨의 Index 페이지로 이동하는 메소드입니다.
     *
     * @return 라벨 목록을 조회하는 메소드 실행 후, 전체 라벨 목록을 담은 List 를 가지고 Index 페이지로 이동합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @GetMapping
    public ModelAndView retrieveLabels() throws UnAuthenticException, UnAuthorizationException {
        List<LabelRetrieveResponse> responses = labelService.retrieveLabels();

        ModelAndView mav = new ModelAndView("pages/admin/labels/index");
        mav.addObject("labels", responses);

        return mav;
    }

    /**
     * 지정한 라벨을 삭제한 후, 다시 라벨의 Index 페이지로 이동하는 메소드입니다.
     *
     * @param labelId - 삭제할 라벨의 식별번호입니다.
     * @return 지정한 라벨을 삭제하는 메소드 실행 후, 라벨의 Index 페이지로 REDIRECT 합니다.
     * @throws UnAuthenticException     - 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
     * @throws UnAuthorizationException - 권한이 없는 사용자가 접근 시 발생하는 예외입니다.
     * @since 1.0.0
     */
    @DeleteMapping("/{labelId}")
    public ModelAndView deleteLabel(@PathVariable @Min(1) final Long labelId)
            throws UnAuthenticException, UnAuthorizationException {
        labelService.deleteLabel(labelId);

        return new ModelAndView(LABEL_DEFAULT);
    }

}
