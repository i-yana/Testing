package ru.nsu.fit.endpoint.service.manager;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import ru.nsu.fit.endpoint.service.database.DBService;
import ru.nsu.fit.endpoint.service.database.data.Plan;

import java.util.List;
import java.util.UUID;

public class PlanManager extends ParentManager {
    public PlanManager(DBService dbService, Logger flowLog) {
        super(dbService, flowLog);
    }

    /**
     * Метод создает новый объект типа Plan. Ограничения:
     * name - длина не больше 128 символов и не меньше 2 включительно не содержит спец символов. Имена не пересекаются друг с другом;
    /* details - длина не больше 1024 символов и не меньше 1 включительно;
    /* fee - больше либо равно 0 но меньше либо равно 999999.
     */
    public Plan createPlan(Plan plan) {

        Validate.notNull(plan, "Argument 'plan' is null.");

        String planName = plan.getName();
        String details = plan.getDetails();
        int fee = plan.getFee();

        Validate.notEmpty(planName, "Name is empty");
        Validate.notEmpty(details, "Details is empty");

        Validate.isTrue(planName.matches("[a-zA-Z]+$")
                        && planName.length()<=128
                        && planName.length()>=2,
                        "PlanName's length should be more or equal 2 symbols and less or equal 128 symbols, not contain special symbols");
        Validate.isTrue(getPlans().stream().noneMatch(p -> p.getName().equals(planName)), "Plan with name already exist");

        Validate.isTrue(details.length()<=1024 && details.length()>=1);
        Validate.isTrue(fee >= 0 && fee <= 999999);

        return dbService.createPlan(plan);
    }

    public Plan updatePlan(Plan plan) {
        throw new NotImplementedException("Please implement the method.");
    }

    public void removePlan(UUID id) {
        throw new NotImplementedException("Please implement the method.");
    }

    /**
     * Метод возвращает список планов доступных для покупки.
     */
    public List<Plan> getPlansByCustomerId(UUID customerId) {
        throw new NotImplementedException("Please implement the method.");
    }
    public List<Plan> getPlans() {
        return dbService.getPlans();
    }
}
