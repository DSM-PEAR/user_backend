package com.dsmpear.main.entity.report.repository;

import com.dsmpear.main.entity.report.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dsmpear.main.entity.report.QReport.report;


@RequiredArgsConstructor
@Repository
public class ReportCustomRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<Report> findAllByAccessAndGradeAndFieldAndType(Access access, Grade grade, Field field, Type type, Pageable pageable) {
        QueryResults<Report> results = jpaQueryFactory
                .select(report)
                .from(report)
                .where(report.isSubmitted.eq(true)
                        .and(report.isAccepted.eq(true)))
                .where(eqGrade(grade)
                        .and(eqType(type))
                        .and(eqAccess(access))
                        .and(eqField(field)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(report.id.desc())
                .fetchResults();




        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression eqAccess(Access access) {
        if (access == null) {
            return null;
        }
        return report.access.eq(access);
    }

    private BooleanExpression eqGrade(Grade grade) {
        if(grade == null) {
            return null;
        }
        return report.grade.eq(grade);
    }

    private BooleanExpression eqField(Field field) {
        if(field == null) {
            return null;
        }
        return report.field.eq(field);
    }

    private BooleanExpression eqType(Type type) {
        if(type == null) {
            return null;
        }
        return report.type.eq(type);
    }
}
