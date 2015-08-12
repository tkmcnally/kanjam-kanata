package models;

import play.core.enhancers.PropertiesEnhancer.GeneratedAccessor;
import play.core.enhancers.PropertiesEnhancer.RewrittenAccessor;
/**
 * Created by missionary on 15-08-12.
 */

@GeneratedAccessor
@RewrittenAccessor
public interface KanJamIdentity {
    String getName();
    String getTicketId();
    String getFirstName();
    String getLastName();
}
