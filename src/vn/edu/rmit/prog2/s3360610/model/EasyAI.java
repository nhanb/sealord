/**
 *
 * @author s3360610 - Bui Thanh Nhan
 */
package vn.edu.rmit.prog2.s3360610.model;

public class EasyAI extends AI {

    public EasyAI(Map map, Map targetMap) {
        super(map, targetMap);
    }

    @Override
    public void shoot() {

        super.shoot();

        Integer targetIndex = randomIndex(targets);

        try {
            targetMap.shoot(targetMap.getSpots().get(targetIndex));
            targets.remove(targetIndex);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
